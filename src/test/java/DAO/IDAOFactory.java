package DAO;

public interface IDAOFactory {
    ICarDAO getCarDAO();
    IClientDAO getClientDAO();
}
