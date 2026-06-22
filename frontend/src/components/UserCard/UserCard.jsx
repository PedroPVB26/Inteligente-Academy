function UserCard({ user }) {
    if (!user) {
        return null;
    }

    return (

        <div className="user-card">

            <p>
                <strong>Nome:</strong> {user.name}
            </p>

            <p>
                <strong>CPF:</strong> {user.cpf}
            </p>

            <p>
                <strong>Email:</strong> {user.email}
            </p>

            <p>
                <strong>Data de Nascimento:</strong> {user.birthDate}
            </p>

            <p>
                <strong>Email:</strong> {user.email}
            </p>

            <p>
                <strong>Tipo:</strong> {user.userRole}
            </p>

            <p>
                <strong>Criado em:</strong> {user.createdAt}
            </p>

            <p>
                <strong>Modificado em:</strong> {user.modifiedAt}
            </p>

            <p>
                <strong>Verificado:</strong>{" "}
                {user.verified ? "Sim" : "Não"}
            </p>

        </div>

    );

}

export default UserCard;