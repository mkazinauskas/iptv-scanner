import React from 'react';
import {
    Container,
    Header,
} from 'semantic-ui-react';

import ChannelsTable from './Table'

class Channels extends React.Component {
    render() {
        return (
            <Container style={{ marginTop: '7em' }}>
                <Header as='h1'>Semantic UI React Fixed Template</Header>
                <p><ChannelsTable /></p>
            </Container >
        );
    }
}

export default Channels;