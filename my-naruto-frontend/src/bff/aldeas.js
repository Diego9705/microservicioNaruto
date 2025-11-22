import axios from 'axios';

const API_URL = 'http://localhost:8080/api/aldeas';

export const getAllAldeas = () => axios.get(API_URL);
export const getAldeaById = (id) => axios.get(`${API_URL}/${id}`);
export const createAldea = (aldea) => axios.post(API_URL, aldea);
export const loadDefaultAldeas = () => axios.get(`${API_URL}/predeterminados`);