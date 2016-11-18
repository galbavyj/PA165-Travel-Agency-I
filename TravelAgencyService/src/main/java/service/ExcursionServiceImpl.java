package service;

import cz.muni.fi.pa165.travelAgency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelAgency.persistence.entity.Excursion;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucia
 */
@Service
public class ExcursionServiceImpl implements ExcursionService {
    
    @Inject
	private ExcursionDao excursionDao;

    @Override
    public Excursion createExcursion(Excursion excursion) {
        excursionDao.create(excursion);
        return excursion;
    }

    @Override
    public void deleteExcursion(Excursion ex) {
        excursionDao.remove(ex);
    }

    @Override
    public void changeDescription(Excursion excursion, String description) {
        excursion.setDescription(description);
        excursionDao.update(excursion);
    }

    @Override
    public void changePrice(Excursion excursion, BigDecimal price) {
        excursion.setPrice(price);
        excursionDao.update(excursion);
    }

    @Override
    public List<Excursion> findAllExcursions() {
        return excursionDao.findAllExcursions();
    }

    @Override
    public Excursion findExcursionById(Long exId) {
        return excursionDao.findExcursionById(exId);
    }

    @Override
    public Long updateExcursion(Excursion ex) {
        excursionDao.update(ex);
        return ex.getId();
    }
    
}
