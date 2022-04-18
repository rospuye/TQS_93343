import React, { memo } from "react";
import {
  ComposableMap,
  Geographies,
  Geography
} from "react-simple-maps";
import { useState, useEffect } from "react";
import { css } from "@emotion/react";
import ClipLoader from "react-spinners/ClipLoader";
import ReactTooltip from "react-tooltip";
const axios = require('axios').default;

const geoUrl =
  "https://raw.githubusercontent.com/zcreativelabs/react-simple-maps/master/topojson-maps/world-110m.json";

const override = css`
  display: block;
  margin: 0 auto;
  border-color: white;
`;

const fetchCountryData = (country_id) => {
  return fetch("http://localhost:8081/countries/name=" + country_id)
    .then((response) => response.json());
}

const getDate = (what_day) => { // what_day = "yesterday", "2 days ago" or "3 days ago"
  var today = new Date();

  var day = "";
  if (what_day === "yesterday") {
    day = (today.getDate() - 1).toString().length === 1 ? ("0" + (today.getDate() - 1)) : (today.getDate() - 1);
  }
  else if (what_day === "2 days ago") {
    day = (today.getDate() - 2).toString().length === 1 ? ("0" + (today.getDate() - 2)) : (today.getDate() - 2);
  }
  else if (what_day === "3 days ago") {
    day = (today.getDate() - 3).toString().length === 1 ? ("0" + (today.getDate() - 3)) : (today.getDate() - 3);
  }
  else {
    return null;
  }

  var month = (today.getMonth() + 1).toString().length === 1 ? ("0" + (today.getMonth() + 1)) : (today.getMonth() + 1);
  var date = today.getFullYear() + '-' + month + '-' + day;

  return date;
}

const MapChart = ({  }) => {

  const [isBusy, setBusy] = useState(true);
  let [loading] = useState(true);
  let [color] = useState("#ffffff");
  const [content, setContent] = useState("");

  useEffect(() => {
    setBusy(true);
    async function fetchData() {
      const url = "http://localhost:8081/countries";

      axios.get(url).then((response) => {
        setBusy(false);
      });
    }

    fetchData();
  }, []);

  return (
    isBusy ?
      <div className="sweet-loading" style={{ marginTop: '10%' }}>
        <ClipLoader color={color} loading={loading} css={override} size={150} />
        <p style={{ color: 'white', textAlign: 'center' }}>Loading...</p>
      </div>
      :
      <>
        <ComposableMap data-tip="" projectionConfig={{
          scale: 160,

        }}
          width={1100}
          height={420}
          style={{
            width: "100%",
            height: "auto",
            outline: "none"
          }}>
          <Geographies geography={geoUrl}>
            {({ geographies }) =>
              geographies.map(geo => (
                <Geography
                  key={geo.rsmKey}
                  geography={geo}
                  onMouseEnter={() => {
                    const { NAME, NAME_LONG } = geo.properties;

                    let data = "";

                    let territory = false;

                    // ------ SPECIAL CASES ------
                    if (NAME_LONG === "United States") {
                      data = fetchCountryData("US");
                    }
                    else if (NAME_LONG === "Falkland Islands") { // external API name: "Falkland Islands (Malvinas)"
                      data = fetchCountryData("United Kingdom"); // it's a territory
                      territory = true;
                    }
                    else if (NAME_LONG === "Russian Federation") {
                      data = fetchCountryData(NAME);
                    }
                    else if (NAME_LONG === "Greenland") {
                      data = fetchCountryData("Denmark"); // it's a territory
                      territory = true;
                    }
                    else if (NAME_LONG === "Puerto Rico") {
                      data = fetchCountryData("US"); // it's a territory
                      territory = true;
                    }
                    else if (NAME_LONG === "New Caledonia") {
                      data = fetchCountryData("France"); // it's a territory
                      territory = true;
                    }
                    else if (NAME_LONG === "Côte d'Ivoire") {
                      data = fetchCountryData("Cote d'Ivoire");
                    }
                    else if (NAME_LONG === "Republic of Korea") {
                      data = fetchCountryData("Korea, South");
                    }
                    else if (NAME_LONG === "Lao PDR") {
                      data = fetchCountryData("Laos");
                    }
                    else if (NAME_LONG === "Brunei Darussalam") {
                      data = fetchCountryData("Brunei");
                    }
                    else if (NAME_LONG === "Czech Republic") {
                      data = fetchCountryData("Czechia");
                    }
                    else if (NAME_LONG === "The Gambia") {
                      data = fetchCountryData("Gambia");
                    }
                    else if (NAME_LONG === "French Southern and Antarctic Lands"
                      || NAME_LONG === "Western Sahara"
                      || NAME_LONG === "Northern Cyprus"
                      || NAME_LONG === "Dem. Rep. Korea"
                      || NAME_LONG === "Timor-Leste"
                      || NAME_LONG === "Myanmar"
                      || NAME_LONG === "Taiwan"
                      || NAME_LONG === "Palestine"
                      || NAME_LONG === "Macedonia"
                      || NAME_LONG === "Montenegro"
                      || NAME_LONG === "Kosovo"
                      || NAME_LONG === "Serbia"
                      || NAME_LONG === "Sri Lanka"
                      || NAME_LONG === "Republic of the Congo"
                      || NAME_LONG === "Democratic Republic of the Congo"
                      || NAME_LONG === "Lybia"
                      || NAME_LONG === "Somaliland"
                      || NAME_LONG === "Turkmenistan"
                      || NAME_LONG === "Swaziland") {

                      // API has no data on these godforsaken lands

                    }

                    // ------ GENERAL CASE ------
                    else {
                      data = fetchCountryData(NAME_LONG);
                    }

                    // PROCESS COUNTRY DATA
                    if (data !== "") {
                      data.then(function (result) {

                        let country_data = "";

                        if (territory) {

                          let ter_name = "";
                          if (NAME_LONG === "Falkland Islands") {
                            ter_name = NAME_LONG + " (Malvinas)";
                          }
                          else {
                            ter_name = NAME_LONG;
                          }

                          country_data = ter_name + "<br/><br/>Confirmed cases:<br/>";

                          for (const territory of result.territories) {

                            if (territory.territory === ter_name) {
                              let cd = territory.confirmed_dates;
                              country_data += getDate("yesterday") + ": " + cd[getDate("yesterday")] + "<br/>";
                              country_data += getDate("2 days ago") + ": " + cd[getDate("2 days ago")] + "<br/>";
                              country_data += getDate("3 days ago") + ": " + cd[getDate("3 days ago")] + "<br/>";

                              country_data += "<br/>Confirmed deaths:<br/>";
                              let dd = territory.deaths_dates;
                              country_data += getDate("yesterday") + ": " + dd[getDate("yesterday")] + "<br/>";
                              country_data += getDate("2 days ago") + ": " + dd[getDate("2 days ago")] + "<br/>";
                              country_data += getDate("3 days ago") + ": " + dd[getDate("3 days ago")] + "<br/>";

                              break;
                            }

                          }

                        }
                        else {
                          country_data = NAME_LONG + "<br/><br/>Confirmed cases:<br/>";

                          let cd = result.all.confirmed_dates;
                          country_data += getDate("yesterday") + ": " + cd[getDate("yesterday")] + "<br/>";
                          country_data += getDate("2 days ago") + ": " + cd[getDate("2 days ago")] + "<br/>";
                          country_data += getDate("3 days ago") + ": " + cd[getDate("3 days ago")] + "<br/>";

                          country_data += "<br/>Confirmed deaths:<br/>";
                          let dd = result.all.deaths_dates;
                          country_data += getDate("yesterday") + ": " + dd[getDate("yesterday")] + "<br/>";
                          country_data += getDate("2 days ago") + ": " + dd[getDate("2 days ago")] + "<br/>";
                          country_data += getDate("3 days ago") + ": " + dd[getDate("3 days ago")] + "<br/>";
                        }

                        setContent(`${country_data}`);

                      });
                    }
                    else {
                      setContent(NAME_LONG + "<br/><br/>No data.");
                    }

                  }}
                  onMouseLeave={() => {
                    setContent("");
                  }}
                  style={{
                    default: {
                      fill: "#FFFFFF",
                      outline: "none"
                    },
                    hover: {
                      fill: "#FCA311",
                      outline: "none"
                    },
                    pressed: {
                      fill: "#CA7D02",
                      outline: "none"
                    }
                  }}
                />
              ))
            }
          </Geographies>
        </ComposableMap>
        <ReactTooltip className="tooltip" html={true}>{content}</ReactTooltip>
      </>
  );
};

export default memo(MapChart);
