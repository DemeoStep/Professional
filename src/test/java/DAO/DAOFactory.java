package DAO;

public class DAOFactory implements IDAOFactory{

    private static IDAOFactory factory;

    public static synchronized IDAOFactory getInstance() {
        if (factory == null) {
            factory = new DAOFactory();
        }
        return factory;
    }

    @Override
    public ICarDAO getCarDAO() {
        return new CarDAO();
    }

    @Override
    public IClientDAO getClientDAO() {
        return new ClientDAO();
    }
}
