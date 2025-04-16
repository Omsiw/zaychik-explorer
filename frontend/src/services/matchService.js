
import axios from '../services/axios';
export const startMatch = async () => {
  const response = await axios.post('/match/search');
  return response.data;
};

export const fetchMap = async (matchId) => {
  const response = await axios.get(`/matches/${matchId}/cells`);
  return response.data;
};
