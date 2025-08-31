const API_BASE_URL = 'http://localhost:8080';

export const fetchAutenticado = async (endpoint, options = {}) => {
  const token = localStorage.getItem('authToken');

  const headers = {
    'Content-type': "application/json",
    ...options.headers,
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
      headers,
  });

  if (!response.ok){
    const erro = await response.json().catch(()=> ({message: response.statusText}));
    throw new Error(erro.message || "Ocorreu um erro");
  }

  if (response.status === 204){
    return;
  }
  return response.json();
}
