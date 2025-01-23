import {boot} from 'quasar/wrappers';
import axios from 'axios';


// Create an Axios instance
const api = axios.create({
  baseURL: 'http://localhost:8080', // Backend URL
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: false, // Set to true if you need cookies or session
});

export default boot(({app}) => {
  // Attach Axios instance to Vue app
  app.config.globalProperties.$axios = axios; // For default Axios
  app.config.globalProperties.$api = api; // For your custom API instance
});

export {api};
