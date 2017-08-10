begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|runners
operator|.
name|MockitoJUnitRunner
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
name|assertTrue
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|MockitoJUnitRunner
operator|.
name|class
argument_list|)
DECL|class|SearchQueryHighlightListenerTest
specifier|public
class|class
name|SearchQueryHighlightListenerTest
block|{
comment|//@Mock
comment|//private ProtectedTermsLoader loader;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
comment|//Globals.prefs = JabRefPreferences.getInstance();
comment|//Globals.protectedTermsLoader = loader;
comment|//when(loader.getProtectedTermsLists()).thenReturn(Collections.emptyList());
block|}
annotation|@
name|Test
DECL|method|dummyTest ()
specifier|public
name|void
name|dummyTest
parameter_list|()
block|{
name|assertTrue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/*     // TODO: Reenable these tests and remove dummyTest     @Test     public void testHighlighting() {          String content = "Test Word Content";         String contentToHighlight1 = "Word";         String contentToHighlight2 = "Content";          TextArea ta = new TextArea("", content);          Highlighter highlighter = ta.getHighlighter();         Highlight[] highlight = highlighter.getHighlights();          //there is no area to highlight!         Assert.assertEquals("Expected no highlighting area ", 0, highlight.length);          ta.highlightPattern(Optional.of(Pattern.compile("Word")));          highlighter = ta.getHighlighter();         highlight = highlighter.getHighlights();          //there is one area to highlight!         Assert.assertEquals("Expected one highlighting area ", 1, highlight.length);         //start of ... Word         Assert.assertEquals(content.indexOf(contentToHighlight1), highlight[0].getStartOffset());          //end of ... word         Assert.assertEquals(content.indexOf(contentToHighlight1) + contentToHighlight1.length(),                 highlight[0].getEndOffset());          //add another word "content" and refresh highlighting         ta.highlightPattern(Optional.of(Pattern.compile("(Word|Content)")));         highlighter = ta.getHighlighter();         highlight = highlighter.getHighlights();          //there are two areas to highlight!         Assert.assertEquals("Expected two highlighting areas ", 2, highlight.length);          //start of ... content         Assert.assertEquals(content.indexOf(contentToHighlight2), highlight[1].getStartOffset());          //end of ... content         Assert.assertEquals(content.indexOf(contentToHighlight2) + contentToHighlight2.length(),                 highlight[1].getEndOffset());          //remove everything and check if highlighting is vanished         ta.highlightPattern(Optional.empty());         highlighter = ta.getHighlighter();         highlight = highlighter.getHighlights();          //there should be none areas to highlight!         Assert.assertEquals("Expected no highlighting area ", 0, highlight.length);     }      @Test     public void testHighlightingContentIndependence() {         String content = "Test Word Content";         TextArea ta = new TextArea("", content);         String textOne = ta.getText();          ta.highlightPattern(Optional.of((Pattern.compile("Word"))));          String textTwo = ta.getText();         Assert.assertEquals("Highlighting may not change content", textOne, textTwo);          //set up empty arraylist and inform the fieldtextarea         ta.highlightPattern(Optional.empty());          String textThree = ta.getText();         Assert.assertEquals("Highlighting may not change content", textOne, textThree);     }      @Test     public void testHighlightingInvalidParameter() {         String content = "Test Word Content";          TextArea ta = new TextArea("", content);          //should not matter at all         ta.highlightPattern(null);     }      */
block|}
end_class

end_unit

