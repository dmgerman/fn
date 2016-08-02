begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2015 Jabref-Team  *  * All programs in this directory and subdirectories are published under the GNU  * General Public License as described below.  *  * This program is free software; you can redistribute it and/or modify it under  * the terms of the GNU General Public License as published by the Free Software  * Foundation; either version 2 of the License, or (at your option) any later  * version.  *  * This program is distributed in the hope that it will be useful, but WITHOUT  * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  * details.  *  * You should have received a copy of the GNU General Public License along with  * this program; if not, write to the Free Software Foundation, Inc., 59 Temple  * Place, Suite 330, Boston, MA 02111-1307 USA  *  * Further information about the GNU GPL is available at:  * http://www.gnu.org/copyleft/gpl.ja.html  *  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

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

begin_class
DECL|class|LatexToUnicodeFormatterTest
specifier|public
class|class
name|LatexToUnicodeFormatterTest
block|{
DECL|field|formatter
specifier|public
specifier|final
name|LatexToUnicodeFormatter
name|formatter
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|testPlainFormat ()
specifier|public
name|void
name|testPlainFormat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"aaa"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatUmlaut ()
specifier|public
name|void
name|testFormatUmlaut
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¤"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\\"{a}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\\"{A}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatStripLatexCommands ()
specifier|public
name|void
name|testFormatStripLatexCommands
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"-"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\mbox{-}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatTextit ()
specifier|public
name|void
name|testFormatTextit
parameter_list|()
block|{
comment|// See #1464
name|assertEquals
argument_list|(
literal|"text"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\textit{text}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEscapedDollarSign ()
specifier|public
name|void
name|testEscapedDollarSign
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"$"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEquationsSingleSymbol ()
specifier|public
name|void
name|testEquationsSingleSymbol
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ï"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\sigma$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEquationsMoreComplicatedFormatting ()
specifier|public
name|void
name|testEquationsMoreComplicatedFormatting
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"A 32\u00A0mA Î£Î-modulator"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"A 32~{mA} {$\\Sigma\\Delta$}-modulator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MÃ¶nch"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChi ()
specifier|public
name|void
name|testChi
parameter_list|()
block|{
comment|// See #1464
name|assertEquals
argument_list|(
literal|"Ï"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\chi$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSWithCaron ()
specifier|public
name|void
name|testSWithCaron
parameter_list|()
block|{
comment|// Bug #1264
name|assertEquals
argument_list|(
literal|"Å "
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\v{S}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCombiningAccentsCase1 ()
specifier|public
name|void
name|testCombiningAccentsCase1
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"hÌ§"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\c{h}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCombiningAccentsCase2 ()
specifier|public
name|void
name|testCombiningAccentsCase2
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aÍ"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\spreadlips{a}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandIsKept ()
specifier|public
name|void
name|unknownCommandIsKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandKeepsArgument ()
specifier|public
name|void
name|unknownCommandKeepsArgument
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"bbbb"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa{bbbb}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandWithEmptyArgumentIsKept ()
specifier|public
name|void
name|unknownCommandWithEmptyArgumentIsKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa{}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

