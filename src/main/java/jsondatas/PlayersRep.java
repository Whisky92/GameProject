package jsondatas;

public class PlayersRep extends GsonRepository<PlayerDatas> {

    /**
     * A constructor for PlayersRep
     */
    public PlayersRep() {
        super(PlayerDatas.class);
    }


}
