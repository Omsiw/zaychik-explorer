
import plainAxios from './plainAxios';
import axios from './axios';

const API_URL = '/auth';

export const register = async (userData) => {
  const response = await plainAxios.post(`${API_URL}/register`, userData);
  return response.data;
};

export const login = async (userData) => {
  const response = await plainAxios.post(`${API_URL}/login`, userData);
  localStorage.setItem('token', response.data.token);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem('token');
};
