begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.net
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|net
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|URLUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|URLUtilTest
class|class
name|URLUtilTest
block|{
annotation|@
name|Test
DECL|method|cleanGoogleSearchURL ()
name|void
name|cleanGoogleSearchURL
parameter_list|()
throws|throws
name|Exception
block|{
comment|// empty text
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|" "
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
comment|// no URL
name|assertEquals
argument_list|(
literal|"this is no url!"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"this is no url!"
argument_list|)
argument_list|)
expr_stmt|;
comment|// no Google search URL
name|assertEquals
argument_list|(
literal|"http://dl.acm.org/citation.cfm?id=321811"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"http://dl.acm.org/citation.cfm?id=321811"
argument_list|)
argument_list|)
expr_stmt|;
comment|// malformed Google URL
name|assertEquals
argument_list|(
literal|"https://www.google.de/urlâ¥"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/urlâ¥"
argument_list|)
argument_list|)
expr_stmt|;
comment|// no queries
name|assertEquals
argument_list|(
literal|"https://www.google.de/url"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?"
argument_list|)
argument_list|)
expr_stmt|;
comment|// no multiple queries
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?key=value"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?key=value"
argument_list|)
argument_list|)
expr_stmt|;
comment|// no key values
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?key"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?key"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?url"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?url"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?key="
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?key="
argument_list|)
argument_list|)
expr_stmt|;
comment|// no url param
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?key=value&key2=value2"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?key=value&key2=value2"
argument_list|)
argument_list|)
expr_stmt|;
comment|// no url param value
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?url="
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?url="
argument_list|)
argument_list|)
expr_stmt|;
comment|// url param value no URL
name|assertEquals
argument_list|(
literal|"https://www.google.de/url?url=this+is+no+url"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?url=this+is+no+url"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Http
name|assertEquals
argument_list|(
literal|"http://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"http://www.google.de/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=http%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Https
name|assertEquals
argument_list|(
literal|"https://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.de/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=https%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
comment|// root domain
name|assertEquals
argument_list|(
literal|"https://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://google.de/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=https%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
comment|// foreign domain
name|assertEquals
argument_list|(
literal|"https://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=https%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
comment|// foreign domain co.uk
name|assertEquals
argument_list|(
literal|"https://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.co.uk/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=https%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
comment|// accept ftp results
name|assertEquals
argument_list|(
literal|"ftp://moz.com/ugc/the-ultimate-guide-to-the-google-search-parameters"
argument_list|,
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
literal|"https://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0CCEQFjAAahUKEwjJurHd2sfHAhWBsxQKHSrEAaM&url=ftp%3A%2F%2Fmoz.com%2Fugc%2Fthe-ultimate-guide-to-the-google-search-parameters&ei=0THeVYmOJIHnUqqIh5gK&usg=AFQjCNHnid_r_d2LP8_MqvI7lQnTC3lB_g&sig2=ICzxDroG2ENTJSUGmdhI2w"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isURLshouldAcceptValidURL ()
name|void
name|isURLshouldAcceptValidURL
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|URLUtil
operator|.
name|isURL
argument_list|(
literal|"http://www.google.com"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|URLUtil
operator|.
name|isURL
argument_list|(
literal|"https://www.google.com"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isURLshouldRejectInvalidURL ()
name|void
name|isURLshouldRejectInvalidURL
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|URLUtil
operator|.
name|isURL
argument_list|(
literal|"www.google.com"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|URLUtil
operator|.
name|isURL
argument_list|(
literal|"google.com"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

