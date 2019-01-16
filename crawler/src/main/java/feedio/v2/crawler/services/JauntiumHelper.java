package feedio.v2.crawler.services;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.NotFound;

public class JauntiumHelper {

	public static Optional<String> getHrefAttribute(Element element) {
		Optional<String> href = Optional.empty();
		try {
			href = Optional.of(element.getAt("href"));
		} catch (NotFound e) {}
		return href;
	}

	public static Optional<String> buildUrl(String webSourceUrl, String webLinkUrl, Optional<String> oHrefAttribute,
			Optional<String> oBaseHref) {

		Optional<String> oTargetUrl = Optional.ofNullable(null);

		if(!oHrefAttribute.isPresent()) {
			return Optional.ofNullable(null);
		}
		
		String hrefAttribute = oHrefAttribute.get();
		
		
		String urlPattern = "^(https?:\\/\\/)?(www\\.)?([\\w-]+\\.)+[‌​\\w]{2,63}\\/?.*$";
		Pattern p = Pattern.compile(urlPattern);
		Matcher m;
		m = p.matcher(hrefAttribute);
		if(m.matches()) {
			return oHrefAttribute;
		}
		
		// If the href attribute starts with a slash, concat it with the webLinkUrl
		if (hrefAttribute.startsWith("/")) {
			oTargetUrl = concat(webLinkUrl, hrefAttribute);
		}

		// If the href attribute doesn't start with a slash, concat it :
		// With base tag href attribute if present
		// With webSourceUrl if base tag href attribute missing
		else {
			if (oBaseHref.isPresent()) {

				oTargetUrl = concat(oBaseHref.get(), hrefAttribute);

			} else {
				oTargetUrl = concat(webSourceUrl, hrefAttribute);
			}
		}
		System.out.println("NEW URL FOUND " + oTargetUrl.get());
		return oTargetUrl;
	}
	
	private static Optional<String> concat(String link, String href) {
		Optional<String> oConcated = Optional.ofNullable(null);
		
		oConcated = Optional.of(link.concat("/").concat(href));

		if (link.endsWith("/")) {
			oConcated = Optional.of(link.concat(href));
			if(href.startsWith("/")) {
				oConcated = Optional.of(link.concat(href.substring(1)));
			}
		}
		
		return oConcated;
	}

	public static boolean isArticle(Browser browser) {

		try {
			browser.doc.findFirst("<meta property=og:type content=article>");
			browser.doc.findFirst("<meta property=og:title>");
			return true;
		} catch (NotFound nfE) {
			return false;
		}

	}
	
	public static boolean belongsToDomain(String link, String domain) {
		if (link != null) {
			String[] r = link.replace("https://", "").replace("http://", "").split("/");
			if (r.length > 0) {
				return r[0].indexOf(domain) != -1;
			}
		}

		return false;
	}

}
