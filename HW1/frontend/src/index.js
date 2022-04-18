import React from "react";
import ReactDOM from "react-dom";

import "./styles.css";

import MapChart from "./MapChart";

function App() {
  return (
    <div>
      <h1 style={{ textAlign: "center" }}>COVID-19 Incidence Data</h1>
      <h4 style={{ textAlign: "center" }}>An Interactive Map</h4>
      <MapChart />
    </div>
  );
}

const rootElement = document.getElementById("root");
ReactDOM.render(<App />, rootElement);
