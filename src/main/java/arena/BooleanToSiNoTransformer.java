package arena;


import mundo.NivelParaJugar;

import com.uqbar.commons.collections.Transformer;

public class BooleanToSiNoTransformer implements Transformer<NivelParaJugar, String> {

	@Override
	public String transform(NivelParaJugar nivelParaJugar) {
		return nivelParaJugar.isCompletado() ? "SÍ" : "NO";
	}

}
