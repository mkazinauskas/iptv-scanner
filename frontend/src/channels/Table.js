import React from 'react';
import { Icon, Menu, Table, Button } from 'semantic-ui-react';
import axios from 'axios';

const AVAILABLE_STATUSES = ['WORKING', 'NOT_WORKING', 'UNKNOWN', 'IN_VALIDATION']

class ChannelsTable extends React.Component {

    state = {
        status: 'WORKING',
        channels: [],
        pagination: {
            first: true,
            last: false,
            number: 0,
            numberOfElements: 20,
            totalPages: 0,
            availablepages: []
        },
        inVerification: new Set()
    }

    verifyAll = () => {
        const inVerification = this.state.inVerification;
        this.state.channels.forEach(channel => {
            inVerification.add(channel.id);
        })
        this.setState({ inVerification }, () => this.loadPage);
        axios.post(`/channels/verification?status=${this.state.status}`)
            .then(res => {
                this.loadPage();
            })
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

    verify = (id) => {
        const inVerification = this.state.inVerification;
        inVerification.add(id);
        this.setState({ inVerification }, () => this.loadPage);
        axios.post(`/channels/${id}/verification`)
            .then(res => {
                this.loadPage();
            })
    }

    componentDidMount() {
        this.loadPage();
    }

    loadPage = () => {
        axios.get(`/channels?sort=id,asc&page=${this.state.pagination.number}`)
            .then(res => {
                const channels = res.data.content;

                const inVerification = this.state.inVerification;

                channels.filter(channel => channel.status !== 'IN_VALIDATION').forEach(channel => {
                    inVerification.delete(channel.id);
                })
                const availablepages = [res.data.number - 2, res.data.number - 1, res.data.number, res.data.number + 1, res.data.number + 2]
                    .filter((it) => it >= 0)
                    .filter((it) => it < res.data.totalPages);

                const pagination = {
                    first: res.data.first,
                    last: res.data.last,
                    number: res.data.number,
                    numberOfElements: res.data.numberOfElements,
                    totalPages: res.data.totalPages,
                    availablepages,
                    inVerification
                }
                if (inVerification.size !== 0) {
                    setTimeout(this.loadPage, 3000);
                }
                this.setState({ channels, pagination, inVerification }, () => this.render());
            })
    }

    render() {
        if (!this.state.channels) {
            return <p>Loading...</p>
        }
        return (
            <div>
                <div style={{ marginBottom: '0.5em' }}>
                    <Button positive class="ui button" onClick={this.verifyAll}>Verify</Button>
                </div>
                <Table>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Id</Table.HeaderCell>
                            <Table.HeaderCell>Creation Date</Table.HeaderCell>
                            <Table.HeaderCell>Name</Table.HeaderCell>
                            <Table.HeaderCell>Status</Table.HeaderCell>
                            <Table.HeaderCell>Sound Track</Table.HeaderCell>
                            <Table.HeaderCell>Uri</Table.HeaderCell>
                            <Table.HeaderCell>Actions</Table.HeaderCell>
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
                                    <Table.Cell>
                                        <Button
                                            positive={!this.state.inVerification.has(item.id)}
                                            class="ui button {buttonType}"
                                            onClick={() => this.verify(item.id)}>
                                            {this.state.inVerification.has(item.id) ? 'Verifying...' : 'Verify'}
                                        </Button>
                                    </Table.Cell>
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
            </div>
        );
    }
}

export default ChannelsTable;