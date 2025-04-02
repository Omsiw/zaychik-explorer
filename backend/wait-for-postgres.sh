#!/bin/sh
set -e

host="postgres"
port=5432
user="postgres"

echo "⏳ Ожидаем PostgreSQL на ${host}:${port}..."

until pg_isready -h "$host" -p "$port" -U "$user"; do
  sleep 2
done

echo "✅ PostgreSQL готов. Запускаем приложение..."
exec "$@"
