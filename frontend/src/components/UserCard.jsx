function UserCard({ usuario }) {

    return (

        <div className="user-card">

            <p>
                <strong>Nome:</strong> {usuario.nome}
            </p>

            <p>
                <strong>CPF:</strong> {usuario.cpf}
            </p>

            <p>
                <strong>Email:</strong> {usuario.email}
            </p>

            <p>
                <strong>Tipo:</strong> {usuario.tipoUsuario}
            </p>

            <p>
                <strong>Criado em:</strong> {usuario.createdAt}
            </p>

            <p>
                <strong>Modificado em:</strong> {usuario.modifiedAt}
            </p>

            <p>
                <strong>Verificado:</strong>{" "}
                {usuario.verificado ? "Sim" : "Não"}
            </p>

        </div>

    );

}

export default UserCard;