package gr.tses.hellothorntail.converter;

import javax.inject.Inject;

import gr.tses.hellothorntail.model.Person;
import gr.tses.hellothorntail.parser.PersonParser;

public class PersonConverter implements org.eclipse.microprofile.config.spi.Converter<Person>{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // does not work
    //@Inject
    private PersonParser parser;
    
    @Override

    
    public Person convert(String value) {
        PersonParser parser = new PersonParser();
    
        return parser.parse(value);
    }
    
}
