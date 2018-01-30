package caramelo.com.br.listacomasynctask.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by logonrm on 29/01/2018.
 */

public class Android implements Serializable {

    private static final String KEY_VERSAO = "versao";
    private static final String KEY_NOME = "nome";
    private static final String KEY_API = "api";
    private static final String KEY_URL_IMAGEM = "urlImagem";

    private String versao;
    private String nome;
    private String api;
    private String urlImagem;

    public Android(JSONObject json) throws JSONException {
        versao = json.getString(KEY_VERSAO);
        nome = json.getString(KEY_NOME);
        api = json.getString(KEY_API);
        urlImagem = json.getString(KEY_URL_IMAGEM);
    }

    public String getVersao() {
        return versao;
    }

    public String getNome() {
        return nome;
    }

    public String getApi() {
        return api;
    }

    public String getUrlImagem() {
        return urlImagem;
    }
}
