
import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

export const register = async (userData) => {
  const response = await axios.post(`${API_URL}/register`, userData);
  localStorage.setItem('token', response.data.token);
  return response.data;
};

export const login = async (userData) => {
  const response = await axios.post(`${API_URL}/login`, userData);
  localStorage.setItem('token', response.data.jwt);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('token');
};

export const getToken = () => localStorage.getItem('token');
