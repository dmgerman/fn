begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_class
DECL|class|UnitFormatter
specifier|public
class|class
name|UnitFormatter
implements|implements
name|LayoutFormatter
block|{
DECL|field|unitList
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|unitList
init|=
operator|new
name|String
index|[]
block|{
literal|"A"
block|,
comment|// Ampere
literal|"Ah"
block|,
comment|// Ampere hours
literal|"B"
block|,
comment|// Byte
literal|"Bq"
block|,
comment|// Bequerel
literal|"C"
block|,
comment|// Coulomb
literal|"F"
block|,
comment|// Farad
literal|"Gy"
block|,
comment|// Gray
literal|"H"
block|,
comment|// Henry
literal|"Hz"
block|,
comment|// Hertz
literal|"J"
block|,
comment|// Joule
literal|"K"
block|,
comment|// Kelvin
literal|"N"
block|,
comment|// Newton
literal|"\\$\\\\Omega\\$"
block|,
comment|// Ohm
literal|"Pa"
block|,
comment|// Pascal
literal|"S"
block|,
comment|// Siemens, Samples
literal|"Sa"
block|,
comment|// Samples
literal|"Sv"
block|,
comment|// Sv
literal|"T"
block|,
comment|// Tesla
literal|"V"
block|,
comment|// Volt
literal|"VA"
block|,
comment|// Volt ampere
literal|"W"
block|,
comment|// Watt
literal|"Wb"
block|,
comment|// Weber
literal|"Wh"
block|,
comment|// Watt hours
literal|"bar"
block|,
comment|// bar
literal|"b"
block|,
comment|// bit
literal|"cd"
block|,
comment|// candela
literal|"dB"
block|,
comment|// decibel
literal|"dBm"
block|,
comment|// decibel
literal|"dBc"
block|,
comment|//decibel
literal|"eV"
block|,
comment|// electron volts
literal|"inch"
block|,
comment|// inch
literal|"kat"
block|,
comment|// katal
literal|"lm"
block|,
comment|// lumen
literal|"lx"
block|,
comment|// lux
literal|"m"
block|,
comment|// meters
literal|"mol"
block|,
comment|// mol
literal|"rad"
block|,
comment|// radians
literal|"s"
block|,
comment|// seconds
literal|"sr"
block|,
comment|// steradians
block|}
decl_stmt|;
DECL|field|unitPrefixList
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|unitPrefixList
init|=
operator|new
name|String
index|[]
block|{
literal|"y"
block|,
comment|// yocto
literal|"z"
block|,
comment|// zepto
literal|"a"
block|,
comment|// atto
literal|"f"
block|,
comment|// femto
literal|"p"
block|,
comment|// pico
literal|"n"
block|,
comment|// nano
literal|"\\$\\\\mu\\$"
block|,
comment|// micro
literal|"u"
block|,
comment|// micro
literal|"m"
block|,
comment|// milli
literal|"c"
block|,
comment|// centi
literal|"d"
block|,
comment|// deci
literal|""
block|,
comment|// no prefix
literal|"da"
block|,
comment|// deca
literal|"h"
block|,
comment|// hekto
literal|"k"
block|,
comment|// kilo
literal|"M"
block|,
comment|// mega
literal|"G"
block|,
comment|// giga
literal|"T"
block|,
comment|// tera
literal|"P"
block|,
comment|// peta
literal|"E"
block|,
comment|// exa
literal|"Z"
block|,
comment|// zetta
literal|"Y"
block|,
comment|// yotta
block|}
decl_stmt|;
DECL|field|unitCombinations
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|unitCombinations
decl_stmt|;
static|static
block|{
name|int
name|uLLength
init|=
name|unitList
operator|.
name|length
decl_stmt|;
name|int
name|uPLLength
init|=
name|unitPrefixList
operator|.
name|length
decl_stmt|;
name|int
name|uCLength
init|=
name|uLLength
operator|*
name|uPLLength
decl_stmt|;
name|unitCombinations
operator|=
operator|new
name|String
index|[
name|uCLength
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|uLLength
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|uPLLength
condition|;
name|j
operator|++
control|)
block|{
name|unitCombinations
index|[
name|i
operator|*
name|uPLLength
operator|+
name|j
index|]
operator|=
name|unitPrefixList
index|[
name|j
index|]
operator|+
name|unitList
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
block|}
DECL|method|format (String text, String [] listOfWords)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|,
name|String
index|[]
name|listOfWords
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Arrays
operator|.
name|sort
argument_list|(
name|listOfWords
argument_list|,
operator|new
name|LengthComparator
argument_list|()
argument_list|)
expr_stmt|;
comment|// LengthComparator from CaseKeeper.java
comment|// Replace the hyphen in 12-bit etc with a non-breaking hyphen, will also avoid bad casing of 12-Bit
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9,\\.]+)-([Bb][Ii][Tt])"
argument_list|,
literal|"$1\\\\mbox\\{-\\}$2"
argument_list|)
expr_stmt|;
comment|// Replace the space in 12 bit etc with a non-breaking space, will also avoid bad casing of 12 Bit
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9,\\.]+) ([Bb][Ii][Tt])"
argument_list|,
literal|"$1~$2"
argument_list|)
expr_stmt|;
comment|// For each word in the list
for|for
control|(
name|String
name|listOfWord
range|:
name|listOfWords
control|)
block|{
comment|// Add {} if the character before is a space, -, /, (, [, or } or if it is at the start of the string but not if it is followed by a }
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9])("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Only add brackets to keep case
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9])-("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1\\\\mbox\\{-\\}\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Replace hyphen with non-break hyphen
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9]) ("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1~\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Replace space with a hard space
block|}
return|return
name|text
return|;
block|}
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|this
operator|.
name|format
argument_list|(
name|text
argument_list|,
name|unitCombinations
argument_list|)
return|;
block|}
block|}
end_class

end_unit

