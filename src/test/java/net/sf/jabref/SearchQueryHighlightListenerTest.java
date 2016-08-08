begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Highlighter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Highlighter
operator|.
name|Highlight
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
name|gui
operator|.
name|fieldeditors
operator|.
name|TextArea
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
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|preferences
operator|.
name|JabRefPreferences
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
name|Mock
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
name|mockito
operator|.
name|Mockito
operator|.
name|when
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
annotation|@
name|Mock
DECL|field|loader
name|ProtectedTermsLoader
name|loader
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|protectedTermsLoader
operator|=
name|loader
expr_stmt|;
name|when
argument_list|(
name|loader
operator|.
name|getProtectedTermsLists
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHighlighting ()
specifier|public
name|void
name|testHighlighting
parameter_list|()
block|{
name|String
name|content
init|=
literal|"Test Word Content"
decl_stmt|;
name|String
name|contentToHighlight1
init|=
literal|"Word"
decl_stmt|;
name|String
name|contentToHighlight2
init|=
literal|"Content"
decl_stmt|;
name|TextArea
name|ta
init|=
operator|new
name|TextArea
argument_list|(
literal|""
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|Highlighter
name|highlighter
init|=
name|ta
operator|.
name|getHighlighter
argument_list|()
decl_stmt|;
name|Highlight
index|[]
name|highlight
init|=
name|highlighter
operator|.
name|getHighlights
argument_list|()
decl_stmt|;
comment|//there is no area to highlight!
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Expected no highlighting area "
argument_list|,
literal|0
argument_list|,
name|highlight
operator|.
name|length
argument_list|)
expr_stmt|;
name|ta
operator|.
name|highlightPattern
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Word"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|highlighter
operator|=
name|ta
operator|.
name|getHighlighter
argument_list|()
expr_stmt|;
name|highlight
operator|=
name|highlighter
operator|.
name|getHighlights
argument_list|()
expr_stmt|;
comment|//there is one area to highlight!
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Expected one highlighting area "
argument_list|,
literal|1
argument_list|,
name|highlight
operator|.
name|length
argument_list|)
expr_stmt|;
comment|//start of ... Word
name|Assert
operator|.
name|assertEquals
argument_list|(
name|content
operator|.
name|indexOf
argument_list|(
name|contentToHighlight1
argument_list|)
argument_list|,
name|highlight
index|[
literal|0
index|]
operator|.
name|getStartOffset
argument_list|()
argument_list|)
expr_stmt|;
comment|//end of ... word
name|Assert
operator|.
name|assertEquals
argument_list|(
name|content
operator|.
name|indexOf
argument_list|(
name|contentToHighlight1
argument_list|)
operator|+
name|contentToHighlight1
operator|.
name|length
argument_list|()
argument_list|,
name|highlight
index|[
literal|0
index|]
operator|.
name|getEndOffset
argument_list|()
argument_list|)
expr_stmt|;
comment|//add another word "content" and refresh highlighting
name|ta
operator|.
name|highlightPattern
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(Word|Content)"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|highlighter
operator|=
name|ta
operator|.
name|getHighlighter
argument_list|()
expr_stmt|;
name|highlight
operator|=
name|highlighter
operator|.
name|getHighlights
argument_list|()
expr_stmt|;
comment|//there are two areas to highlight!
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Expected two highlighting areas "
argument_list|,
literal|2
argument_list|,
name|highlight
operator|.
name|length
argument_list|)
expr_stmt|;
comment|//start of ... content
name|Assert
operator|.
name|assertEquals
argument_list|(
name|content
operator|.
name|indexOf
argument_list|(
name|contentToHighlight2
argument_list|)
argument_list|,
name|highlight
index|[
literal|1
index|]
operator|.
name|getStartOffset
argument_list|()
argument_list|)
expr_stmt|;
comment|//end of ... content
name|Assert
operator|.
name|assertEquals
argument_list|(
name|content
operator|.
name|indexOf
argument_list|(
name|contentToHighlight2
argument_list|)
operator|+
name|contentToHighlight2
operator|.
name|length
argument_list|()
argument_list|,
name|highlight
index|[
literal|1
index|]
operator|.
name|getEndOffset
argument_list|()
argument_list|)
expr_stmt|;
comment|//remove everything and check if highlighting is vanished
name|ta
operator|.
name|highlightPattern
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
name|highlighter
operator|=
name|ta
operator|.
name|getHighlighter
argument_list|()
expr_stmt|;
name|highlight
operator|=
name|highlighter
operator|.
name|getHighlights
argument_list|()
expr_stmt|;
comment|//there should be none areas to highlight!
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Expected no highlighting area "
argument_list|,
literal|0
argument_list|,
name|highlight
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHighlightingContentIndependence ()
specifier|public
name|void
name|testHighlightingContentIndependence
parameter_list|()
block|{
name|String
name|content
init|=
literal|"Test Word Content"
decl_stmt|;
name|TextArea
name|ta
init|=
operator|new
name|TextArea
argument_list|(
literal|""
argument_list|,
name|content
argument_list|)
decl_stmt|;
name|String
name|textOne
init|=
name|ta
operator|.
name|getText
argument_list|()
decl_stmt|;
name|ta
operator|.
name|highlightPattern
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|(
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Word"
argument_list|)
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|textTwo
init|=
name|ta
operator|.
name|getText
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Highlighting may not change content"
argument_list|,
name|textOne
argument_list|,
name|textTwo
argument_list|)
expr_stmt|;
comment|//set up empty arraylist and inform the fieldtextarea
name|ta
operator|.
name|highlightPattern
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|textThree
init|=
name|ta
operator|.
name|getText
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Highlighting may not change content"
argument_list|,
name|textOne
argument_list|,
name|textThree
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHighlightingInvalidParameter ()
specifier|public
name|void
name|testHighlightingInvalidParameter
parameter_list|()
block|{
name|String
name|content
init|=
literal|"Test Word Content"
decl_stmt|;
name|TextArea
name|ta
init|=
operator|new
name|TextArea
argument_list|(
literal|""
argument_list|,
name|content
argument_list|)
decl_stmt|;
comment|//should not matter at all
name|ta
operator|.
name|highlightPattern
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

