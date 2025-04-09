import React, { useState } from 'react';
import { login } from '../services/authService';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [form, setForm] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      await login(form);
      navigate('/profile');
    } catch (err) {
      alert('Ошибка входа. Проверьте логин/пароль.');
    }
  };

  return (
    <div className="page-content">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Вход</h2>
        <input name="username" value={form.username} onChange={handleChange} placeholder="Имя пользователя" />
        <input name="password" type="password" value={form.password} onChange={handleChange} placeholder="Пароль" />
        <button type="submit">Войти</button>
      </form>
    </div>
  );
}

export default LoginPage;
