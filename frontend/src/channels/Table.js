import React from 'react';
import { Icon, Menu, Table } from 'semantic-ui-react';
import axios from 'axios';

class ChannelsTable extends React.Component {

    state = {
        channels: [],
        pagination: {
            first: true,
            last: false,
            number: 0,
            numberOfElements: 20,
            totalPages: 0,
            availablepages: []
        }
    }

    toPage = (pageNumber) => {
        const pagination = this.state.pagination;
        pagination.number = pageNumber;
        this.setState({ pagination }, () => this.loadPage());
    }

    nextPage = (event) => {
        const pagination = this.state.pagination;
        pagination.number++
        this.setState({ pagination }, () => this.loadPage());
    }

    previousPage = (event) => {
        const pagination = this.state.pagination;
        pagination.number--
        this.setState({ pagination }, () => this.loadPage());
    }

    componentDidMount() {
        this.loadPage();
    }

    loadPage = () => {
        axios.get(`http://localhost:8080/channels?sort=id,asc&page=${this.state.pagination.number}`)
            .then(res => {
                const channels = res.data.content;
                const availablepages = [res.data.number - 2, res.data.number - 1, res.data.number, res.data.number + 1, res.data.number + 2]
                    .filter((it) => it >= 0)
                    .filter((it) => it < res.data.totalPages);

                const pagination = {
                    first: res.data.first,
                    last: res.data.last,
                    number: res.data.number,
                    numberOfElements: res.data.numberOfElements,
                    totalPages: res.data.totalPages,
                    availablepages
                }
                this.setState({ channels, pagination }, () => this.render());
            })
    }

    render() {
        console.warn(this.state.channels + 'aa');
        if (!this.state.channels) {
            return <p>Loading...</p>
        }
        return (
            <Table>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>Id</Table.HeaderCell>
                        <Table.HeaderCell>Creation Date</Table.HeaderCell>
                        <Table.HeaderCell>Name</Table.HeaderCell>
                        <Table.HeaderCell>Status</Table.HeaderCell>
                        <Table.HeaderCell>Sound Track</Table.HeaderCell>
                        <Table.HeaderCell>Uri</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    {this.state.channels.map(item => {
                        return (
                            <Table.Row key={item.id}>
                                <Table.Cell>{item.id}</Table.Cell>
                                <Table.Cell>{item.creationDate}</Table.Cell>
                                <Table.Cell>{item.name}</Table.Cell>
                                <Table.Cell>{item.status}</Table.Cell>
                                <Table.Cell>{item.soundTrack}</Table.Cell>
                                <Table.Cell>{item.uri}</Table.Cell>
                            </Table.Row>
                        )
                    })
                    }
                </Table.Body>
                <Table.Footer>
                    <Table.Row>
                        <Table.HeaderCell colSpan='6'>
                            <Menu floated='right' pagination>
                                <Menu.Item as='a' icon disabled={this.state.pagination.first} onClick={this.previousPage}>
                                    <Icon name='chevron left' />
                                </Menu.Item>
                                {this.state.pagination.availablepages.map(page => {
                                    return (<Menu.Item key={page} onClick={() => this.toPage(page)} active={this.state.pagination.number === page}>{page + 1}</Menu.Item>)
                                })}
                                <Menu.Item as='a' icon disabled={this.state.pagination.last} onClick={this.nextPage}>
                                    <Icon name='chevron right' />
                                </Menu.Item>
                            </Menu>
                        </Table.HeaderCell>
                    </Table.Row>
                </Table.Footer>
            </Table>
        );
    }
}

export default ChannelsTable;