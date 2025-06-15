💆‍♀️ Clínica de Estética e Cosmética - Sistema Web
Este projeto é um sistema completo para uma clínica de estética e cosmética, desenvolvido com foco em facilitar o agendamento e o gerenciamento de consultas tanto para pacientes quanto para profissionais da saúde estética.

✨ Funcionalidades
👩‍⚕️ Para o Paciente:
Cadastro e login seguro

Escolha de procedimentos estéticos disponíveis

Agendamento de consultas com:

Nome do paciente

Telefone

Profissional desejado

Procedimento

Data, horário e observações

Visualização de todas as suas consultas

Cancelamento e remarcação de consultas

🧑‍⚕️ Para o Profissional:
Cadastro e login seguro

Visualização de todas as consultas marcadas com ele

Cancelamento de consultas

Geração de relatórios em PDF com os agendamentos

🛠️ Tecnologias Utilizadas
🔹 Front-end:
React com JavaScript

Axios para requisições HTTP

Desenvolvido com Visual Studio Code

🔹 Back-end:
Java com Spring Boot

IDE Eclipse

Banco de dados MySQL

Testes com Postman

🔹 Segurança:
BCrypt para criptografar senhas

JWT (JSON Web Token) para autenticação de usuários

CORS configurado para comunicação segura entre frontend e backend

☁️ Deploy
Front-end: Vercel

Back-end: Microsoft Azure

Banco de Dados: Aiven for MySQL

📄 Relatórios e PDF
Profissionais têm acesso a um recurso exclusivo para exportar suas consultas em formato PDF, facilitando o acompanhamento e organização da agenda.

🔒 Autenticação
Autenticação baseada em tokens JWT

Todas as senhas são armazenadas de forma segura com bcrypt

Middleware de proteção de rotas com verificação de permissões de acesso

🚀 Como rodar o projeto localmente
Backend:
1. Clone o repositório do backend
2. Importe o projeto no Eclipse
3. Configure o application.properties com seu banco de dados MySQL
4. Rode a aplicação
   
Frontend:
1. Clone o repositório do frontend
2. Execute `npm install`
3. Execute `npm start`
   
💡 Observações
O sistema é dividido por permissões de acesso (paciente e profissional)
Todo o fluxo de autenticação está protegido com tokens e criptografia
