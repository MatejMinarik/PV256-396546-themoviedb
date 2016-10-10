package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Huvart on 10/10/16.
 */

public class MoviesContainer {

    private static MoviesContainer sInstance;
    private ArrayList<Movie> mMovieList = new ArrayList<>();

    private MoviesContainer() {
        initFilmList();
    }

    public static MoviesContainer getInstance() {
        if (sInstance == null) {
            sInstance = new MoviesContainer();
        }
        return sInstance;
    }

    private void initFilmList() {
        for (int i = 1; i < 5 ; i++) {
            m
            
            
            //mMovieList.add(new Movie(R.drawable.americka_krasa, getCurrentTime().getTime(), "Na první pohled jsou Burnhamovi šťastnou rodinou, jejíž život je zalit sluncem. Pohodlný dům, auto kvalitní značky, půvabná dospívající dcera, studující na správné škole, rodiče úspěšní ve svém povolání. Nikdo se tedy nediví, že paní Burnhamová (Annette Bening) je věčně samý úsměv - alespoň na veřejnosti. Ve skutečnosti je ale všechno jinak. Obraz dokonalosti matce Carolyn a dceři Jane neustále kazí otec Lester (Kevin Spacey). Není tím ramenatým, snědým \"plejbojem\" a co je horší, začíná stále častěji dávat najevo, že se mu nedaří a že je nespokojen se svým životem. Dokud jen odmítá roli, kterou stovky jemu podobných odevzdaně hrají, aby měli klid, je to pro rodinu sice obtížné, ale únosné. Ještě včera nerozhodný a zakřiknutý Lester se ovšem rozhodne \"krizi středního věku\" řešit nečekaně radikálně. Dojde mu totiž, o co v životě jde: o splnění snů a tužeb, které člověk má. Lester už nikdy nebude ubožák!", "Americka krasa"));
            //mMovieList.add(new Movie(R.drawable.angelika, getCurrentTime().getTime(), "Sedmnáctiletá Angelika je proti své vůli provdána za bohatého toulouského hraběte Joffreye de Peyrac, který je nejen o dvanáct let starší než ona, ale má také pověst čaroděje. Svým šarmem a inteligencí si brzy získá Angeličino srdce a zdá se, že jejich šťastný život nemůže nic ohrozit. Jednoho dne je ale Peyrac uvržen do Bastily a upálen na hranici za údajné čarodějnictví. Angelika se uchýlí s dětmi na Dvůr zázraků a s pomocí banditů plánuje pomstu všem, kteří změnili její pohádku ve zlý sen.", "Angelika"));
            //mMovieList.add(new Movie(R.drawable.rivalove, getCurrentTime().getTime(), "Závratná rychlost, adrenalin proudící v žilách, krásné dívky a nezkrotná touha po vítězství. Závody Formule 1 jsou místem, kde jediná chyba může znamenat smrt. Na těchto okruzích spolu soupeří dva odvěcí rivalové. James Hunt je neřízená střela, neodolatelný playboy a brilantní řidič. Niki Lauda je dokonalý profesionál, vždy precizní a disciplinovaný. Movie Rivalové vypráví skutečný příběh dvou pilotů, kteří jsou soky nejen na okruzích, ale také ve skutečném životě. Přestože jsou soupeři, jsou také jeden pro druhého inspirací. Přes jejich rozdílnost je v životě pojí zvláštní přátelství. Ale rivalita je žene ke stále riskantnějším výkonům. Titul mistra světa může získat jen jeden...", "Rivalove"));
            //mMovieList.add(new Movie(R.drawable.rolling_stones, getCurrentTime().getTime(), "Pohled režiséra Martina Scorseseho na největší rock'n'rollovou kapelu, na legendární Rolling Stones, kteří jsou spolu přes 45 let a stále fungují. Scorsese sestavil velice silný režijní tým z osob, z nichž mnoho obdrželo Oscary za svoji práci, a natáčel Stouny v Beacon Theater v New Yorku po dobu dvou dní, aby zachytil jejich syrovou sílu a přinesl divákům tento filmově/hudební zážitek.", "Rolling Stones"));
            mMovieList.add(new Movie(R.drawable.letec, getCurrentTime().getTime(), "Život Howarda Hughese patří k nejvíce fascinujícím příběhům, jaké Amerika předchozího století zrodila. Tento letecký průkopník, řežisér i producent, ropný magnát a muž nejedné další činnosti a tváře totiž vynikal nejen svým všestranným talentem, ale i svou výstřední povahou. A film THE AVIATOR v režii legendárního Martina Scorseseho (Zuřící býk, Mafiáni) se bude soustředit právě na nejzajímavější úsek Hughesovy (Leonardo DiCaprio) životní dráhy. Tedy období, kdy natáčel své filmy, testoval letadla vlastní výroby a proháněl hollywoodské krásky Katharine Hepburn (Cate Blanchett), Avu Gardner (Kate Beckinsale) a Jean Harlow (Gwen Stefani).", "Letec"));
            mMovieList.add(new Movie(R.drawable.stolety_starik, getCurrentTime().getTime(), "Allanu Karlssonovi je 100 let. V domově důchodců, kde v tomto věku tráví svůj zbývající čas, pro něj chystají velkou narozeninovou oslavu. Ale vitální stařík ještě nechce zůstat v bačkorách v křesle. Má docela jiné plány. Rozhodne se pro útěk. Poté, co vyleze z okna, se vydává na úžasný a naprosto nepředvídatelný výlet... Shodou náhod se mu do rukou dostane kufr plný peněz. Než se naděje, má v patách gang zločinců, sem tam se objeví nějaká mrtvola a mezi společníky, které na své cestě přibere do party, je i velmi neskladná slonice. Kromě toho po něm samozřejmě pátrá policejní sbor v čele s nepříliš schopným komisařem. Pro kohokoliv jiného by to bylo životní dobrodružství, ale s podobnými věcmi už má Allan bohaté zkušenosti. I když se nikdy nezajímal o politiku, byl nechtěně hlavním aktérem mnoha důležitých událostí 20. století. Bezděčně se podílel na vynálezu atomové bomby, stal se dobrým přítelem několika amerických prezidentů a mezi jeho další \"známé\" se při jeho životní pouti zařadili i Stalin, Churchill, Mao Ce-tung nebo Kim Ir-sen.", "Stolety Starik"));
            mMovieList.add(new Movie(R.drawable.mafianovi, getCurrentTime().getTime(), "Kdysi měl všechny a všechno pod palcem. Ale časy se mění. Bývalý mafiánský boss Giovanni Manzoni (Robert De Niro), jeho rázná manželka Maggie (Michelle Pfeiffer) a jejich dvě děti jsou za příkladnou spolupráci se zákonem umístěni do programu na ochranu svědků. Žít životem spořádaných občanů se jim příliš nedaří. Zoufalý agent FBI Stansfield (Tommy Lee Jones) je musí opět přestě-hovat, protože mafiánské klany zuřivě pátrají po špinavém udavači s jasným cílem. A tak se z nich tentokrát stávají Blakeovi a je- jich novým domovem malé, ospalé městečko v Normandii. Jenže staří psi se novým kous-kům učí těžko. Jejich chování a zvyklosti jsou pro život v utajení překážkou. Tahle rodinka má mafiánství prostě v krvi.", "Mafianovi"));
        }
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }

    public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        mMovieList = movieList;
    }
}
