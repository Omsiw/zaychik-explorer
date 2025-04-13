
import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

export const register = async (userData) => {
  const response = await axios.post(`${API_URL}/register`, userData);
};

export const login = async (userData) => {
  const response = await axios.post(`${API_URL}/login`, userData);
};

export const logout = () => {

};


