package eu.javaca.playground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.javaca.playground.service.FieldManager;
import eu.javaca.playground.structure.GenericNode;

import java.util.ArrayList;
import java.util.logging.Logger;

public class InstantiableImpl implements Instantiable {

    private static final Logger LOGGER = Logger.getLogger(InstantiableImpl.class.getName());

    private final FieldManager fieldManager;

    public InstantiableImpl() {
        this.fieldManager = new FieldManager();
    }

    @Override
    public String instantiate(Class any, Target target) {
        String result = "";
        try {
            GenericNode rootNode = fieldManager.instantiateClass(any, new ArrayList<>(), new ArrayList<>());
            switch (target) {
                case JSON: {
                    ObjectMapper om = new ObjectMapper();
                    result = om.writeValueAsString(rootNode);
                    break;
                }
                case XML: {
                    XmlMapper xmlMapper = new XmlMapper();
                    result = xmlMapper.writeValueAsString(rootNode);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return result;
    }
}
