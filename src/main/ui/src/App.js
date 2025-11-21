import React, { useState, useEffect } from 'react';
import axios from 'axios';
import LogService from "./utils/LogService";

function CustomerManager() {
    const [customers, setCustomers] = useState([]);
    const [editingId, setEditingId] = useState(null);
    const [editFormData, setEditFormData] = useState({ name: '', email: '', phone: '' });
    const [newCustomer, setNewCustomer] = useState({ name: '', email: '', phone: '' });

    useEffect(() => {
        fetchCustomers();
    }, []);

    const handleError = (error, stepDescription) => {
        const errorMessage = `Error in ${stepDescription}: ${error}`;
        LogService.sendLog(errorMessage, 'ERROR');
    };

    const fetchCustomers = async () => {
        try {
            const res = await axios.get('/api/customers');
            setCustomers(res.data);
        } catch (err) {
            handleError(err, 'Error fetching customers');
        }
    };

    const handleEditChange = (e) => {
        setEditFormData({ ...editFormData, [e.target.name]: e.target.value });
    };

    const handleNewChange = (e) => {
        setNewCustomer({ ...newCustomer, [e.target.name]: e.target.value });
    };

    const handleEditClick = (customer) => {
        setEditingId(customer.id);
        setEditFormData({ name: customer.name, email: customer.email, phone: customer.phone });
    };

    const handleCancelClick = () => {
        setEditingId(null);
        setEditFormData({ name: '', email: '', phone: '' });
    };

    const handleUpdate = async (id) => {
        try {
            await axios.put(`/api/customers/${id}`, editFormData);
            setEditingId(null);
            setEditFormData({ name: '', email: '', phone: '' });
            fetchCustomers();
        } catch (err) {
            handleError(err, 'Error updating customer');
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`/api/customers/${id}`);
            fetchCustomers();
        } catch (err) {
            handleError(err, 'Error deleting customer');
        }
    };

    const handleAdd = async () => {
        if (!newCustomer.name || !newCustomer.email) return;
        try {
            await axios.post('/api/customers', newCustomer);
            setNewCustomer({ name: '', email: '', phone: '' });
            fetchCustomers();
        } catch (err) {
            handleError(err, 'Error adding customer');
        }
    };

    return (
        <div>
            <h1>Customer Management System</h1>
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {customers.map((c) => (
                    <tr key={c.id}>
                        {editingId === c.id ? (
                            <>
                                <td>
                                    <input
                                        type="text"
                                        name="name"
                                        value={editFormData.name}
                                        onChange={handleEditChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="email"
                                        name="email"
                                        value={editFormData.email}
                                        onChange={handleEditChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="text"
                                        name="phone"
                                        value={editFormData.phone}
                                        onChange={handleEditChange}
                                    />
                                </td>
                                <td>
                                    <button onClick={() => handleUpdate(c.id)}>Update</button>
                                    <button onClick={handleCancelClick}>Cancel</button>
                                </td>
                            </>
                        ) : (
                            <>
                                <td>{c.name}</td>
                                <td>{c.email}</td>
                                <td>{c.phone}</td>
                                <td>
                                    <button onClick={() => handleEditClick(c)}>Edit</button>
                                    <button onClick={() => handleDelete(c.id)}>Delete</button>
                                </td>
                            </>
                        )}
                    </tr>
                ))}

                <tr>
                    <td>
                        <input
                            type="text"
                            name="name"
                            placeholder="Name"
                            value={newCustomer.name}
                            onChange={handleNewChange}
                        />
                    </td>
                    <td>
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={newCustomer.email}
                            onChange={handleNewChange}
                        />
                    </td>
                    <td>
                        <input
                            type="text"
                            name="phone"
                            placeholder="Phone"
                            value={newCustomer.phone}
                            onChange={handleNewChange}
                        />
                    </td>
                    <td>
                        <button onClick={handleAdd}>Add</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}

export default CustomerManager;
