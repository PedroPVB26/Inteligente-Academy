function UserCard({ user }) {

    return (

        <div className="user-card">

            <p>
                <strong>Nome:</strong> {user.nome}
            </p>

            <p>
                <strong>CPF:</strong> {user.cpf}
            </p>

            <p>
                <strong>Email:</strong> {user.email}
            </p>

            <p>
                <strong>Tipo:</strong> {user.tipouser}
            </p>

            <p>
                <strong>Criado em:</strong> {user.createdAt}
            </p>

            <p>
                <strong>Modificado em:</strong> {user.modifiedAt}
            </p>

            <p>
                <strong>Verificado:</strong>{" "}
                {user.verificado ? "Sim" : "Não"}
            </p>

        </div>

    );

}

export default UserCard;