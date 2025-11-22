import axios from 'axios';

const API_URL = 'http://localhost:8070/api/jutsus';

export const getAllJutsus = () => axios.get(API_URL);
export const getJutsuById = (id) => axios.get(`${API_URL}/${id}`);
export const createJutsu = (jutsu) => axios.post(API_URL, jutsu);
export const loadDefaultJutsus = () => axios.get(`${API_URL}/predeterminados`);