package ServerTickets.DAO;

import ServerTickets.DTO.PriceListDTO;
import ServerTickets.DTO.SaleDTO;

public class SalesDAOImpl implements SalesDAO{
    @Override
    public long GetEventsByDistributor(String distributor) {
        return 0;
    }

    @Override
    public PriceListDTO GetPriceListByEvent(long eventId) {
        return null;
    }

    @Override
    public void SaleRegister(SaleDTO sale) {

    }
}
