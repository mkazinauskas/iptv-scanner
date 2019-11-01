import React from 'react'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import {
  Container,
  Menu,
} from 'semantic-ui-react'
import Channels from "./channels/Channels";

const FixedMenuLayout = () => (
  <div>
    <Router>
      <Menu fixed='top' inverted>
        <Container>
          <Menu.Item as='a' header>
            IPTV Scanner
        </Menu.Item>
          <Menu.Item><Link to="/">Home</Link></Menu.Item>
        </Container>
      </Menu>

      <Switch>
        <Route exact path="/">
          <Channels />
        </Route>
        <Route path="/todo">
          <Channels />
        </Route>
      </Switch>
    </Router>
  </div>
)

export default FixedMenuLayout