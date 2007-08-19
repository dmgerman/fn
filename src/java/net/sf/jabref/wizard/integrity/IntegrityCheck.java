begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 R. Nagel  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_comment
comment|// created by : r.nagel 27.10.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : check all bibtex items and report errors, inconsistencies,
end_comment

begin_comment
comment|//            warnings, hints and ....
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//     todo : find equal authors: e.g.: D. Knuth = Donald Knuth = Donald E. Knuth
end_comment

begin_comment
comment|//            and try to give all items an identically look
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.integrity
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|integrity
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|IntegrityCheck
specifier|public
class|class
name|IntegrityCheck
block|{
DECL|field|messages
specifier|private
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
decl_stmt|;
DECL|method|IntegrityCheck ()
specifier|public
name|IntegrityCheck
parameter_list|()
block|{
name|messages
operator|=
operator|new
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
argument_list|()
expr_stmt|;
block|}
DECL|method|checkBibtexDatabase (BibtexDatabase base)
specifier|public
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexDatabase
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|messages
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|base
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|base
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|checkSingleEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
argument_list|(
name|messages
argument_list|)
return|;
block|}
DECL|method|checkBibtexEntry (BibtexEntry entry)
specifier|public
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
name|checkBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|messages
operator|.
name|clear
argument_list|()
expr_stmt|;
name|checkSingleEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
return|return
operator|new
name|Vector
argument_list|<
name|IntegrityMessage
argument_list|>
argument_list|(
name|messages
argument_list|)
return|;
block|}
DECL|method|checkSingleEntry (BibtexEntry entry)
specifier|public
name|void
name|checkSingleEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
return|return ;
name|Object
name|data
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
name|authorNameCheck
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"author"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
name|authorNameCheck
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"editor"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
name|titleCheck
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"title"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|data
operator|=
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
expr_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
name|yearCheck
argument_list|(
name|data
operator|.
name|toString
argument_list|()
argument_list|,
literal|"year"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
comment|/** fills the class Vector (of IntegrityMessage Objects) which did inform about   *  failures, hints....   *  The Authors or Editors field could be invalid -> try to detect it!   *  Knuth, Donald E. and Kurt Cobain and A. Einstein = N,NNaNNaNN   */
DECL|method|authorNameCheck (String names, String fieldName, BibtexEntry entry)
specifier|private
name|void
name|authorNameCheck
parameter_list|(
name|String
name|names
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
comment|// try to extract the structure of author tag
comment|// N = name, ","= seperator, "a" = and
name|StringBuffer
name|structure
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|len
init|=
name|names
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|mode
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|len
condition|;
name|t
operator|++
control|)
block|{
name|char
name|ch
init|=
name|names
operator|.
name|charAt
argument_list|(
name|t
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|ch
condition|)
block|{
case|case
literal|','
case|:
if|if
condition|(
name|mode
operator|==
literal|5
condition|)
comment|// "and"
name|structure
operator|.
name|append
argument_list|(
literal|'a'
argument_list|)
expr_stmt|;
else|else
name|structure
operator|.
name|append
argument_list|(
literal|'N'
argument_list|)
expr_stmt|;
name|structure
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|mode
operator|=
literal|0
expr_stmt|;
break|break ;
case|case
literal|' '
case|:
if|if
condition|(
name|mode
operator|==
literal|5
condition|)
comment|// "and"
name|structure
operator|.
name|append
argument_list|(
literal|'a'
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|mode
operator|!=
literal|0
condition|)
name|structure
operator|.
name|append
argument_list|(
literal|'N'
argument_list|)
expr_stmt|;
name|mode
operator|=
operator|-
literal|1
expr_stmt|;
comment|// blank processed
break|break ;
case|case
literal|'a'
case|:
if|if
condition|(
name|mode
operator|==
operator|-
literal|1
condition|)
name|mode
operator|=
literal|2
expr_stmt|;
break|break ;
case|case
literal|'n'
case|:
if|if
condition|(
name|mode
operator|==
literal|2
condition|)
name|mode
operator|=
literal|3
expr_stmt|;
break|break ;
case|case
literal|'d'
case|:
if|if
condition|(
name|mode
operator|==
literal|3
condition|)
name|mode
operator|=
literal|5
expr_stmt|;
break|break ;
default|default :
name|mode
operator|=
literal|1
expr_stmt|;
block|}
block|}
if|if
condition|(
name|mode
operator|==
literal|5
condition|)
comment|// "and"
name|structure
operator|.
name|append
argument_list|(
literal|'a'
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|mode
operator|!=
literal|0
condition|)
name|structure
operator|.
name|append
argument_list|(
literal|'N'
argument_list|)
expr_stmt|;
comment|// Check
name|len
operator|=
name|structure
operator|.
name|length
argument_list|()
expr_stmt|;
if|if
condition|(
name|len
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|structure
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|!=
literal|'N'
condition|)
comment|// must start by name
block|{
name|messages
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|IntegrityMessage
operator|.
name|NAME_START_WARNING
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|//        back.add("beginning of " +fieldName +" field");
block|}
if|if
condition|(
name|structure
operator|.
name|charAt
argument_list|(
name|structure
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|!=
literal|'N'
condition|)
comment|// end without seperator
block|{
name|messages
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|IntegrityMessage
operator|.
name|NAME_END_WARNING
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|//        back.add("bad end (" +fieldName +" field)");
block|}
if|if
condition|(
name|structure
operator|.
name|indexOf
argument_list|(
literal|"NN,NN"
argument_list|)
operator|>
operator|-
literal|1
condition|)
block|{
name|messages
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|IntegrityMessage
operator|.
name|NAME_SEMANTIC_WARNING
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|//        back.add("something could be wrong in " +fieldName +" field") ;
block|}
block|}
comment|//    messages.add( new IntegrityMessage( IntegrityMessage.NAME_END_WARNING,
comment|//                                        entry, fieldName, null))  ;
block|}
DECL|method|titleCheck (String title, String fieldName, BibtexEntry entry)
specifier|private
name|void
name|titleCheck
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|int
name|len
init|=
name|title
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|mode
init|=
literal|0
decl_stmt|;
name|int
name|upLowCounter
init|=
literal|0
decl_stmt|;
comment|//    boolean lastWasSpace = false ;
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|len
condition|;
name|t
operator|++
control|)
block|{
name|char
name|ch
init|=
name|title
operator|.
name|charAt
argument_list|(
name|t
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|ch
condition|)
block|{
case|case
literal|'}'
case|:
comment|// end of Sequence
if|if
condition|(
name|mode
operator|==
literal|0
condition|)
block|{
comment|// closing brace '}' without an opening
name|messages
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|IntegrityMessage
operator|.
name|UNEXPECTED_CLOSING_BRACE_FAILURE
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
comment|// mode == 1
block|{
name|mode
operator|--
expr_stmt|;
comment|//            lastWasSpace = true ;
block|}
break|break ;
case|case
literal|'{'
case|:
comment|// open {
name|mode
operator|++
expr_stmt|;
break|break ;
case|case
literal|' '
case|:
comment|//          lastWasSpace = true ;
break|break ;
default|default :
if|if
condition|(
name|mode
operator|==
literal|0
condition|)
comment|// out of {}
block|{
if|if
condition|(
name|Character
operator|.
name|isUpperCase
argument_list|(
name|ch
argument_list|)
operator|&&
operator|(
name|t
operator|>
literal|1
operator|)
condition|)
block|{
name|upLowCounter
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|upLowCounter
operator|>
literal|0
condition|)
block|{
comment|/*         Morten Alver (2006.10.10):          Disabling this warning because we have a feature for automatically adding         braces when saving, which makes this warning misleading. It could be modified         to suggest to use this feature if not enabled, and not give a warning if the         feature is enabled.          messages.add( new IntegrityMessage( IntegrityMessage.UPPER_AND_LOWER_HINT,                                         entry, fieldName, null))  ;*/
block|}
block|}
comment|/** Checks, if the number String contains a four digit year */
DECL|method|yearCheck (String number, String fieldName, BibtexEntry entry)
specifier|private
name|void
name|yearCheck
parameter_list|(
name|String
name|number
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|int
name|len
init|=
name|number
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|digitCounter
init|=
literal|0
decl_stmt|;
name|boolean
name|fourDigitsBlock
init|=
literal|false
decl_stmt|;
name|boolean
name|containsFourDigits
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|len
condition|;
name|t
operator|++
control|)
block|{
name|char
name|ch
init|=
name|number
operator|.
name|charAt
argument_list|(
name|t
argument_list|)
decl_stmt|;
if|if
condition|(
name|Character
operator|.
name|isDigit
argument_list|(
name|ch
argument_list|)
condition|)
block|{
name|digitCounter
operator|++
expr_stmt|;
if|if
condition|(
name|digitCounter
operator|==
literal|4
condition|)
name|fourDigitsBlock
operator|=
literal|true
expr_stmt|;
else|else
name|fourDigitsBlock
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|fourDigitsBlock
condition|)
name|containsFourDigits
operator|=
literal|true
expr_stmt|;
name|digitCounter
operator|=
literal|0
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|(
operator|!
name|containsFourDigits
operator|)
operator|&&
operator|(
operator|!
name|fourDigitsBlock
operator|)
condition|)
block|{
name|messages
operator|.
name|add
argument_list|(
operator|new
name|IntegrityMessage
argument_list|(
name|IntegrityMessage
operator|.
name|FOUR_DIGITS_HINT
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

