package midien.kheldiente.cma;

import midien.kheldiente.cma.data.source.remote.CelpaApiHelper;
import midien.kheldiente.cma.data.source.remote.CelpaApiService;
import midien.kheldiente.cma.data.source.remote.FarmerRemoteDataSource;

import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FarmerRemoteDataSourceTest {

    private static final String TAG = FarmerRemoteDataSourceTest.class.getSimpleName();

    public FarmerRemoteDataSource farmerRemoteDataSource;

    @Before
    public void init() {
        farmerRemoteDataSource = new FarmerRemoteDataSource();
    }

    @Test
    public void getFarmer() {
        farmerRemoteDataSource.getFarmer(String.valueOf(1));
    }

    @Test
    public void doesFarmerExists() {
        CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
        celpaApiService.getFarmer("kheldiente", "mikediente123");
    }
}