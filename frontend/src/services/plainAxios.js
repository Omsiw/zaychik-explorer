
import axios from 'axios';

const plainAxios = axios.create({
  baseURL: 'http://localhost:8080',
});

export default plainAxios;
