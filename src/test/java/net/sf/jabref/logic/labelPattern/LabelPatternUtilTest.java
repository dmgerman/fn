begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|labelPattern
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|LabelPatternUtilTest
specifier|public
class|class
name|LabelPatternUtilTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|LabelPatternUtil
operator|.
name|setDataBase
argument_list|(
operator|new
name|BibtexDatabase
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAndInAuthorName ()
specifier|public
name|void
name|testAndInAuthorName
parameter_list|()
block|{
name|BibtexEntry
name|entry0
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Simon Holland}}"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Holland"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAndAuthorNames ()
specifier|public
name|void
name|testAndAuthorNames
parameter_list|()
block|{
name|String
name|bibtexString
init|=
literal|"@ARTICLE{whatevery, author={Mari D. Herland and Mona-Iren Hauge and Ingeborg M. Helgeland}}"
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|bibtexString
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"HerlandHaugeHelgeland"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry
argument_list|,
literal|"authors3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test for https://sourceforge.net/forum/message.php?msg_id=4498555      * Test the Labelmaker and all kind of accents      * Ã Ã¡ Ä Ä Ã Ã© Ã Ã­ Ä¹ Äº Å Å Ã Ã³ Å Å Å Å Ã Ãº Ã Ã½ Å¹ Åº      */
annotation|@
name|Test
DECL|method|testMakeLabelAndCheckLegalKeys ()
specifier|public
name|void
name|testMakeLabelAndCheckLegalKeys
parameter_list|()
block|{
name|BibtexEntry
name|entry0
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas KÃ¶ning}, year={2000}}"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Koen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Aoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Ioen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas Ä¹Ã¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Loen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÅÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Noen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Ooen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÅÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Roen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÅÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Soen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Uoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Yoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas Å¹Ã¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Zoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test the Labelmaker and with accent grave      * Chars to test: "ÃÃÃÃÃ";      */
annotation|@
name|Test
DECL|method|testMakeLabelAndCheckLegalKeysAccentGrave ()
specifier|public
name|void
name|testMakeLabelAndCheckLegalKeysAccentGrave
parameter_list|()
block|{
name|BibtexEntry
name|entry0
init|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Aoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Eoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Ioen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Ooen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry0
operator|=
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{kohn, author={Andreas ÃÃ¶ning}, year={2000}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Uoen"
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry0
argument_list|,
literal|"auth3"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests if checkLegalKey replaces Non-ASCII chars.      * There are quite a few chars that should be replaced. Perhaps there is a better method than the current.      *      * @see Util#checkLegalKey(String)      */
annotation|@
name|Test
DECL|method|testCheckLegalKey ()
specifier|public
name|void
name|testCheckLegalKey
parameter_list|()
block|{
comment|// not tested/ not in hashmap UNICODE_CHARS:
comment|// Å Å   Å Å Å° Å±   Ä¿ Å   Ä¦ Ä§   Ã Ã° Ã Ã¾   Å Å   Ã Ã¦ Ã Ã¸ Ã Ã¥   Æ É Ä Ä   Å® Å¯	Ç¢ Ç£ Ç Ç Ç Ç
comment|//" Ç¢ Ç£ Ç Ç Ç Ç   " +
comment|//"Ä Ä   Å® Å¯  " +
comment|//"Å Å   Å Å Å° Å±   Ä¿ Å   Ä¦ Ä§   Ã Ã° Ã Ã¾   Å Å   Ã Ã¦ Ã Ã¸ Ã Ã¥   Æ É
name|String
name|accents
init|=
literal|"ÃÃ ÃÃ¨ÃÃ¬ÃÃ²ÃÃ¹ Ã Ã¢ Ä Ä Ã Ãª Ä Ä Ä¤ Ä¥ Ã Ã® Ä´ Äµ Ã Ã´ Å Å Ã Ã» Å´ Åµ Å¶ Å·"
decl_stmt|;
name|String
name|expectedResult
init|=
literal|"AaEeIiOoUuAaCcEeGgHhIiJjOoSsUuWwYy"
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"ÃÃ¤ÃÃ«ÃÃ¯ÃÃ¶ÃÃ¼Å¸Ã¿"
expr_stmt|;
name|expectedResult
operator|=
literal|"AeaeEeIiOeoeUeueYy"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ã Ã§ Ä¢ Ä£ Ä¶ Ä· Ä» Ä¼ Å Å Å Å Å Å Å¢ Å£"
expr_stmt|;
name|expectedResult
operator|=
literal|"CcGgKkLlNnRrSsTt"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ä Ä Ä Ä Ä Ä Ä¬ Ä­ Å Å Å¬ Å­"
expr_stmt|;
name|expectedResult
operator|=
literal|"AaEeGgIiOoUu"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ä Ä Ä Ä Ä  Ä¡ Ä° Ä± Å» Å¼"
expr_stmt|;
name|expectedResult
operator|=
literal|"CcEeGgIiZz"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ä Ä Ä Ä Ä® Ä¯ Çª Ç« Å² Å³"
expr_stmt|;
name|expectedResult
operator|=
literal|"AaEeIiOoUu"
expr_stmt|;
comment|// O or Q? o or q?
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ä Ä Ä Ä Äª Ä« Å Å Åª Å« È² È³"
expr_stmt|;
name|expectedResult
operator|=
literal|"AaEeIiOoUuYy"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"Ç Ç Ä Ä Ä Ä Ä Ä Ç Ç Ä½ Ä¾ Å Å Ç Ç Å Å Å  Å¡ Å¤ Å¥ Ç Ç Å½ Å¾"
expr_stmt|;
name|expectedResult
operator|=
literal|"AaCcDdEeIiLlNnOoRrSsTtUuZz"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|expectedResult
operator|=
literal|"AaEeIiNnOoUuYy"
expr_stmt|;
name|accents
operator|=
literal|"ÃÃ£áº¼áº½Ä¨Ä©ÃÃ±ÃÃµÅ¨Å©á»¸á»¹"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|accents
operator|=
literal|"á¸ á¸ á¸¤ á¸¥ á¸¶ á¸· á¸¸ á¸¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹¢ á¹£ á¹¬ á¹­"
expr_stmt|;
name|expectedResult
operator|=
literal|"DdHhLlLlMmNnRrRrSsTt"
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedResult
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|accents
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|totest
init|=
literal|"Ã Ã  Ã Ã¨ Ã Ã¬ Ã Ã² Ã Ã¹   Ã Ã¢ Ä Ä Ã Ãª Ä Ä Ä¤ Ä¥ Ã Ã® Ä´ Äµ Ã Ã´ Å Å Ã Ã» Å´ Åµ Å¶ Å·  Ã Ã¤ Ã Ã« Ã Ã¯ Ã Ã¶ Ã Ã¼ Å¸ Ã¿    "
operator|+
literal|"Ã Ã£ áº¼ áº½ Ä¨ Ä© Ã Ã± Ã Ãµ Å¨ Å© á»¸ á»¹   Ã Ã§ Ä¢ Ä£ Ä¶ Ä· Ä» Ä¼ Å Å Å Å Å Å Å¢ Å£"
operator|+
literal|" Ç Ç Ä Ä Ä Ä Ä Ä Ç Ç Ä½ Ä¾ Å Å Ç Ç Å Å Å  Å¡ Å¤ Å¥ Ç Ç Å½ Å¾   "
operator|+
literal|"Ä Ä Ä Ä Äª Ä« Å Å Åª Å« È² È³"
operator|+
literal|"Ä Ä Ä Ä Ä Ä Ä¬ Ä­ Å Å Å¬ Å­   "
operator|+
literal|"Ä Ä Ä Ä Ä  Ä¡ Ä° Ä± Å» Å¼   Ä Ä Ä Ä Ä® Ä¯ Çª Ç« Å² Å³   "
operator|+
literal|"á¸ á¸ á¸¤ á¸¥ á¸¶ á¸· á¸¸ á¸¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹ á¹¢ á¹£ á¹¬ á¹­   "
decl_stmt|;
name|String
name|expectedResults
init|=
literal|"AaEeIiOoUuAaCcEeGgHhIiJjOoSsUuWwYyAeaeEeIiOeoeUeueYy"
operator|+
literal|"AaEeIiNnOoUuYyCcGgKkLlNnRrSsTt"
operator|+
literal|"AaCcDdEeIiLlNnOoRrSsTtUuZz"
operator|+
literal|"AaEeIiOoUuYy"
operator|+
literal|"AaEeGgIiOoUu"
operator|+
literal|"CcEeGgIiZzAaEeIiOoUu"
operator|+
literal|"DdHhLlLlMmNnRrRrSsTt"
decl_stmt|;
name|assertEquals
argument_list|(
name|expectedResults
argument_list|,
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|totest
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFirstAuthor ()
specifier|public
name|void
name|testFirstAuthor
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Newton"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstAuthor
argument_list|(
literal|"I. Newton and J. Maxwell and A. Einstein and N. Bohr and Harry Unknown"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Newton"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstAuthor
argument_list|(
literal|"I. Newton"
argument_list|)
argument_list|)
expr_stmt|;
comment|// https://sourceforge.net/forum/message.php?msg_id=4498555
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"K{\\\"o}ning"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstAuthor
argument_list|(
literal|"K{\\\"o}ning"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|LabelPatternUtil
operator|.
name|firstAuthor
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|LabelPatternUtil
operator|.
name|firstAuthor
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ignored
parameter_list|)
block|{          }
block|}
annotation|@
name|Test
DECL|method|testAuthIniN ()
specifier|public
name|void
name|testAuthIniN
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"NMEB"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton and J. Maxwell and A. Einstein and N. Bohr and Harry Unknown"
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"NMEB"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton and J. Maxwell and A. Einstein and N. Bohr"
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"NeME"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton and J. Maxwell and A. Einstein"
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"NeMa"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton and J. Maxwell"
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Newt"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"N"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Newton"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Newton"
argument_list|,
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|"I. Newton"
argument_list|,
literal|7
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|LabelPatternUtil
operator|.
name|authIniN
argument_list|(
literal|null
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ignored
parameter_list|)
block|{          }
block|}
annotation|@
name|Test
DECL|method|testFirstPage ()
specifier|public
name|void
name|testFirstPage
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"7"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"7--27"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"27"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"--27"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"42"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"42--111"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"7"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"7,41,73--97"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"7"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"41,7,73--97"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"43"
argument_list|,
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|"43+"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|LabelPatternUtil
operator|.
name|firstPage
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ignored
parameter_list|)
block|{          }
block|}
annotation|@
name|Test
DECL|method|testLastPage ()
specifier|public
name|void
name|testLastPage
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"27"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"7--27"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"27"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"--27"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"111"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"42--111"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"97"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"7,41,73--97"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"97"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"7,41,97--73"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"43"
argument_list|,
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|"43+"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|LabelPatternUtil
operator|.
name|lastPage
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ignored
parameter_list|)
block|{          }
block|}
block|}
end_class

end_unit

