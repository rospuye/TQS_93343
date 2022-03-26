package tqsdemo.employeemngr.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqsdemo.employeemngr.data.Employee;
import tqsdemo.employeemngr.data.EmployeeRepository;
import tqsdemo.employeemngr.service.EmployeeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test scenario: verify the logic of the Service, mocking the response of the
 * datasource
 * Results in standard unit test with mocks
 */
@ExtendWith(MockitoExtension.class)
class B_EmployeeService_UnitTest {

    // mocking the responses of the repository (i.e., no database will be used)
    // lenient is required because we load more expectations in the setup
    // than those used in some tests. As an alternative, the expectations
    // could move into each test method and be trimmed (no need for lenient then)
    @Mock(lenient = true)
    private EmployeeRepository employeeRepository;

    // the entity whose functionality we want to test
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        // these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        // telling the mock employee repository what it should return
        // for each query/request
        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

    // test the "get by name" functionality (for a valid name)
    @Test
    void whenSearchValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }

    // test the "get by name" functionality (for an invalid name,
    // should return null)
    @Test
    void whenSearchInvalidName_thenEmployeeShouldNotBeFound() {
        Employee fromDb = employeeService.getEmployeeByName("wrong_name");
        assertThat(fromDb).isNull();

        // function defined below
        verifyFindByNameIsCalledOnce("wrong_name");
    }

    // when we search by a valid name, the employee should exist
    @Test
    void whenValidName_thenEmployeeShouldExist() {
        boolean doesEmployeeExist = employeeService.exists("john");
        assertThat(doesEmployeeExist).isEqualTo(true);

        // function defined below
        verifyFindByNameIsCalledOnce("john");
    }

    // when we search by an invalid name, the employee should not exist
    @Test
    void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist = employeeService.exists("some_name");
        assertThat(doesEmployeeExist).isEqualTo(false);

        // function defined below
        verifyFindByNameIsCalledOnce("some_name");
    }

    // when we search by a valid ID, the employee should be found
    @Test
    void whenValidId_thenEmployeeShouldBeFound() {
        Employee fromDb = employeeService.getEmployeeById(111L);
        assertThat(fromDb.getName()).isEqualTo("john");

        // function defined below
        verifyFindByIdIsCalledOnce();
    }

    // when we search by an invalid ID, the employee should not be found
    @Test
    void whenInValidId_thenEmployeeShouldNotBeFound() {
        Employee fromDb = employeeService.getEmployeeById(-99L);
        // function defined below
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    // given that we set up a mock repository with 3 employees, when we
    // call the getAllEmployees method on the employeeService, 3 records
    // should be returned
    @Test
    void given3Employees_whengetAll_thenReturn3Records() {
        Employee alex = new Employee("alex", "alex@deti.ua.pt");
        Employee john = new Employee("john", "john@deti.ua.pt");
        Employee bob = new Employee("bob", "bob@deti.ua.pt");

        List<Employee> allEmployees = employeeService.getAllEmployees();
        // function defined below
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(),
                bob.getName());
    }

    // functions used above

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
    }
}
