import React, { createContext, useState, useContext } from 'react';
import {jwtDecode} from 'jwt-decode';

const AuthContext = createContext(null);


export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const token = localStorage.getItem('authToken');
    if (token){
      return jwtDecode(token);
    }
    return null;
  });
  const login = (token) => {
  localStorage.setItem('authToken', token);
  setUser(jwtDecode(token));
  };
  
  const logout = () => {
    localStorage.removeItem('authToken');
    setUser(null);
  };

  const value = { user, login, logout};

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
};

export const useAuth = () => {
  return useContext(AuthContext);
}
