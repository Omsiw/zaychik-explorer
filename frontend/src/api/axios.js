const API_URL = import.meta.env.VITE_API_URL + '/auth';

export const register = async (userData) => {
  const response = await axios.post(`${API_URL}/register`, userData);
  localStorage.setItem('token', response.data.token);
  return response.data;
};
