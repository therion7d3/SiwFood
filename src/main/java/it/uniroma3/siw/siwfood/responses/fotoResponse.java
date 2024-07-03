package it.uniroma3.siw.siwfood.responses;

public class fotoResponse {
    private final Long id;
    private final Long ricettaId;
    private final Long userId;
    private final byte[] data;


    public fotoResponse(Long id, byte[] data, Long ricettaId, Long userId) {
        this.id = id;
        this.data = data;
        this.ricettaId = ricettaId;
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public byte[] getTitle() {
        return this.data;
    }

    public Long getRicettaId() {
        return this.ricettaId;
    }

    public Long getUserId() {
        return this.userId;
    }
}
