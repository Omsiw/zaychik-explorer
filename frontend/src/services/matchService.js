
import axios from '../api/axios';
export const startMatch = async () => {
  const response = await axios.post('/match/search');
  return response.data;
};

export const fetchMap = async (matchId) => {
  const response = await axios.get(`/matches/${matchId}/cells`);
  return response.data;
};

export const moveToAction = async (matchId, targetCellNum) => {
  const response = await axios.post('/action/move', { matchId, targetCellNum });
  return response.data;
};

export const takeCellAction = async (matchId, cellId) => {
  const response = await axios.post('/action/take', { matchId, cellId });
  return response.data;
};