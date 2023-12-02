package ClinicaHospitalar;

public enum Label {
    CARDIOLOGISTA, DERMATOLOGISTA, NEUROLOGISTA, OFTALMOLOGISTA, ODONTOLOGISTA,
    GERAL, GINECOLOGISTA;

    static Label verificarEspecialidade(String diag) throws Exception {
        String[] arg = diag.split(" ");
        Label label = null;
        for (String arg1 : arg) {
            switch (arg1) {
                case "coracao":
                case "enfarte":
                case "cardiaco":
                case "coagulacao":
                case "coronaria":
                case "cardiopatia":
                case "colesterol":
                case "trombose":
                case "marcapasso":
                case "aneurisma":
                case "infarto":
                case "hipertensao":
                case "arritmia":
                    label = CARDIOLOGISTA;
                    break;
                case "pele":
                case "manchas":
                case "impetigo":
                case "dermatite":
                case "micose":
                case "dermico":
                case "botox":
                case "queratose":
                case "celulite":
                case "cicatrizacao":
                case "melanoma":
                case "vitiligo":
                case "acne":
                case "cutaneas":
                    label = DERMATOLOGISTA;
                    break;
                case "cerebro":
                case "encefalite":
                case "ataxia":
                case "parkinson":
                case "epilepsia":
                case "esclerose":
                case "enxaqueca":
                case "sinapse":
                case "tourette":
                case "avc":
                case "neuropatia":
                case "neuronio":
                case "nervoso":
                case "cerebral":
                case "cabeca":
                case "neuromuscular":
                case "cefalia":
                case "huntinton":
                case "tomografia":
                    label = NEUROLOGISTA;
                    break;
//                case "dente", "oral", "siso", "bucal", "dentaria", "halito", "gengivite", "protese", "periodental", "carie", "dentario", "bruxismo", "canal", "dentista", "endodentia", "clareamento": {
//                    label = ODONTOLOGISTA;
//                    break;
//                }
//                case "olho", "lente", "esclerite", "lentes", "visual", "pterigio", "cornea", "estrabismo", "catarata", "conjuntivite", "glaucoma", "retina", "retinopexia", "fundoscopia", "miopia", "refracao", "visao", "vista", "lagrima", "astigmatismo", "retinopatia": {
//                    label = OFTALMOLOGISTA;
//                    break;
//                }
//                case "utero", "ciclo", "menstrual", "menstruacao", "gravidez", "colo", "anticoncepcional", "dst", "urinaria", "mama", "mulher", "menopausa": {
//                    label = GINECOLOGISTA;
//                    break;
//                }
                default:
                    label = GERAL;
            }
        }
        return label;
    }

    static Label converter(String label) throws Exception {
        switch (label.toLowerCase()) {
            case "dermatologista": {
                return Label.DERMATOLOGISTA;
            }
            case "neurologista": {
                return Label.NEUROLOGISTA;
            }
            case "odontologista": {
                return Label.ODONTOLOGISTA;
            }
            case "oftalmologista": {
                return Label.OFTALMOLOGISTA;
            }
            case "cardiologista": {
                return Label.CARDIOLOGISTA;
            }
            case "geral": {
                return Label.GERAL;
            }
            case "ginecologista": {
                return Label.GINECOLOGISTA;
            }
            default:
                throw new Exception("Especialidade nao existe no nosso banco de dados");
        }
    }
}
