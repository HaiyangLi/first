import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;
import play.twirl.api.Content;
import org.junit.Test;


public class ApplicationTest {

	@Test
	public void testIndex() {
		Result result = controllers.Application.index();
	    assertThat(status(result)).isEqualTo(OK);
	    assertThat(contentType(result)).isEqualTo("text/html");
	    assertThat(charset(result)).isEqualTo("utf-8");
	}

}
