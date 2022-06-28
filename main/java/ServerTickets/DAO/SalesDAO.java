package ServerTickets.DAO;

import ServerTickets.DTO.PriceListDTO;
import ServerTickets.DTO.SaleDTO;

public interface SalesDAO {
    public long GetEventsByDistributor(String distributor);
    public PriceListDTO GetPriceListByEvent(long eventId);
    public void SaleRegister(SaleDTO sale);
}
