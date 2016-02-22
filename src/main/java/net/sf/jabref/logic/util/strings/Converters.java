begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015-2016 JabRef contributors.     Copyright (C) 2015-2016 Oscar Gustafsson.      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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
name|exporter
operator|.
name|layout
operator|.
name|format
operator|.
name|FormatChars
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|HTMLToLatexFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnicodeToLatexFormatter
import|;
end_import

begin_comment
comment|/**  * Class with static methods for converting strings from HTML and Unicode to LaTeX encoding.  *  * @author Oscar Gustafsson  */
end_comment

begin_class
DECL|class|Converters
specifier|public
class|class
name|Converters
block|{
DECL|field|HTML_CONVERTER
specifier|private
specifier|static
specifier|final
name|HTMLToLatexFormatter
name|HTML_CONVERTER
init|=
operator|new
name|HTMLToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|UNICODE_CONVERTER
specifier|private
specifier|static
specifier|final
name|UnicodeToLatexFormatter
name|UNICODE_CONVERTER
init|=
operator|new
name|UnicodeToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|LATEX_TO_UNICODE
specifier|public
specifier|static
specifier|final
name|LatexToUnicodeConverter
name|LATEX_TO_UNICODE
init|=
operator|new
name|LatexToUnicodeConverter
argument_list|()
decl_stmt|;
DECL|field|UNICODE_TO_LATEX
specifier|public
specifier|static
specifier|final
name|UnicodeToLatexConverter
name|UNICODE_TO_LATEX
init|=
operator|new
name|UnicodeToLatexConverter
argument_list|()
decl_stmt|;
DECL|field|HTML_TO_LATEX
specifier|public
specifier|static
specifier|final
name|HTMLToLatexConverter
name|HTML_TO_LATEX
init|=
operator|new
name|HTMLToLatexConverter
argument_list|()
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|Converter
argument_list|>
name|ALL
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|Converters
operator|.
name|HTML_TO_LATEX
argument_list|,
name|Converters
operator|.
name|UNICODE_TO_LATEX
argument_list|,
name|Converters
operator|.
name|LATEX_TO_UNICODE
argument_list|)
decl_stmt|;
DECL|interface|Converter
specifier|public
interface|interface
name|Converter
block|{
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|convert (String input)
name|String
name|convert
parameter_list|(
name|String
name|input
parameter_list|)
function_decl|;
block|}
DECL|class|UnicodeToLatexConverter
specifier|public
specifier|static
class|class
name|UnicodeToLatexConverter
implements|implements
name|Converter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Unicode to LaTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|convert (String input)
specifier|public
name|String
name|convert
parameter_list|(
name|String
name|input
parameter_list|)
block|{
return|return
name|Converters
operator|.
name|UNICODE_CONVERTER
operator|.
name|format
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
DECL|class|LatexToUnicodeConverter
specifier|public
specifier|static
class|class
name|LatexToUnicodeConverter
implements|implements
name|Converter
block|{
DECL|field|formatter
specifier|private
specifier|final
name|FormatChars
name|formatter
init|=
operator|new
name|FormatChars
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"LaTeX to Unicode"
return|;
block|}
annotation|@
name|Override
DECL|method|convert (String input)
specifier|public
name|String
name|convert
parameter_list|(
name|String
name|input
parameter_list|)
block|{
return|return
name|formatter
operator|.
name|format
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
DECL|class|HTMLToLatexConverter
specifier|public
specifier|static
class|class
name|HTMLToLatexConverter
implements|implements
name|Converter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"HTML to LaTeX"
return|;
block|}
annotation|@
name|Override
DECL|method|convert (String input)
specifier|public
name|String
name|convert
parameter_list|(
name|String
name|input
parameter_list|)
block|{
return|return
name|Converters
operator|.
name|HTML_CONVERTER
operator|.
name|format
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

