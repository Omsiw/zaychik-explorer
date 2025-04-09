import React, { useState } from 'react';
import { register } from '../services/authService';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
  const [form, setForm] = useState({ username: '', password: '', email: '' });
  const navigate = useNavigate();

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      await register(form);
      navigate('/profile');
    } catch (err) {
      alert('Ошибка при регистрации');
    }
  };

  return (
    <div className="page-content">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Регистрация</h2>
        <input name="username" value={form.username} onChange={handleChange} placeholder="Имя пользователя" type="text" />
        <input name="email" value={form.email} onChange={handleChange} placeholder="Email" type="text" />
        <input name="password" type="password" value={form.password} onChange={handleChange} placeholder="Пароль" />
        <button type="submit">Зарегистрироваться</button>
      </form>
    </div>
  );
}

export default RegisterPage;
