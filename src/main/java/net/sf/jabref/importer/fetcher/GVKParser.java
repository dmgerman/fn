begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * License: GPLv2, but Jan Frederik Maas agreed to change license upon request  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
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
name|bibtex
operator|.
name|EntryTypes
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
name|ImportFormatReader
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
name|model
operator|.
name|entry
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_class
DECL|class|GVKParser
specifier|public
class|class
name|GVKParser
block|{
DECL|method|parseEntries (InputStream is)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|parseEntries
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|IOException
block|{
name|DocumentBuilder
name|dbuild
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|Document
name|content
init|=
name|dbuild
operator|.
name|parse
argument_list|(
name|is
argument_list|)
decl_stmt|;
return|return
name|this
operator|.
name|parseEntries
argument_list|(
name|content
argument_list|)
return|;
block|}
DECL|method|parseEntries (Document content)
specifier|public
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|parseEntries
parameter_list|(
name|Document
name|content
parameter_list|)
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// used for creating test cases
comment|// XMLUtil.printDocument(content);
comment|// Namespace srwNamespace = Namespace.getNamespace("srw","http://www.loc.gov/zing/srw/");
comment|// Schleife ueber allen Teilergebnissen
comment|//Element root = content.getDocumentElement();
name|Element
name|root
init|=
operator|(
name|Element
operator|)
name|content
operator|.
name|getElementsByTagName
argument_list|(
literal|"zs:searchRetrieveResponse"
argument_list|)
operator|.
name|item
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Element
name|srwrecords
init|=
name|getChild
argument_list|(
literal|"zs:records"
argument_list|,
name|root
argument_list|)
decl_stmt|;
if|if
condition|(
name|srwrecords
operator|==
literal|null
condition|)
block|{
comment|// no records found -> return empty list
return|return
name|result
return|;
block|}
name|List
argument_list|<
name|Element
argument_list|>
name|records
init|=
name|getChildren
argument_list|(
literal|"zs:record"
argument_list|,
name|srwrecords
argument_list|)
decl_stmt|;
for|for
control|(
name|Element
name|record
range|:
name|records
control|)
block|{
name|Element
name|e
init|=
name|getChild
argument_list|(
literal|"zs:recordData"
argument_list|,
name|record
argument_list|)
decl_stmt|;
name|e
operator|=
name|getChild
argument_list|(
literal|"record"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|result
operator|.
name|add
argument_list|(
name|parseEntry
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|parseEntry (Element e)
specifier|private
name|BibtexEntry
name|parseEntry
parameter_list|(
name|Element
name|e
parameter_list|)
block|{
name|String
name|author
init|=
literal|null
decl_stmt|;
name|String
name|editor
init|=
literal|null
decl_stmt|;
name|String
name|title
init|=
literal|null
decl_stmt|;
name|String
name|publisher
init|=
literal|null
decl_stmt|;
name|String
name|year
init|=
literal|null
decl_stmt|;
name|String
name|address
init|=
literal|null
decl_stmt|;
name|String
name|series
init|=
literal|null
decl_stmt|;
name|String
name|edition
init|=
literal|null
decl_stmt|;
name|String
name|isbn
init|=
literal|null
decl_stmt|;
name|String
name|issn
init|=
literal|null
decl_stmt|;
name|String
name|number
init|=
literal|null
decl_stmt|;
name|String
name|pagetotal
init|=
literal|null
decl_stmt|;
name|String
name|volume
init|=
literal|null
decl_stmt|;
name|String
name|pages
init|=
literal|null
decl_stmt|;
name|String
name|journal
init|=
literal|null
decl_stmt|;
name|String
name|ppn
init|=
literal|null
decl_stmt|;
name|String
name|booktitle
init|=
literal|null
decl_stmt|;
name|String
name|url
init|=
literal|null
decl_stmt|;
name|String
name|note
init|=
literal|null
decl_stmt|;
name|String
name|quelle
init|=
literal|""
decl_stmt|;
name|String
name|mak
init|=
literal|""
decl_stmt|;
name|String
name|subtitle
init|=
literal|""
decl_stmt|;
name|String
name|entryType
init|=
literal|"book"
decl_stmt|;
comment|// Default
comment|// Alle relevanten Informationen einsammeln
name|List
argument_list|<
name|Element
argument_list|>
name|datafields
init|=
name|getChildren
argument_list|(
literal|"datafield"
argument_list|,
name|e
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Element
argument_list|>
name|iter
init|=
name|datafields
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Element
name|datafield
init|=
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// System.out.println(datafield.getAttributeValue("tag"));
comment|// mak
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"002@"
argument_list|)
condition|)
block|{
name|mak
operator|=
name|getSubfield
argument_list|(
literal|"0"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//ppn
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"003@"
argument_list|)
condition|)
block|{
name|ppn
operator|=
name|getSubfield
argument_list|(
literal|"0"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//author
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"028A"
argument_list|)
condition|)
block|{
name|String
name|vorname
init|=
name|getSubfield
argument_list|(
literal|"d"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
name|String
name|nachname
init|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|author
operator|=
name|author
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|author
operator|=
literal|""
expr_stmt|;
block|}
name|author
operator|=
name|author
operator|.
name|concat
argument_list|(
name|vorname
operator|+
literal|" "
operator|+
name|nachname
argument_list|)
expr_stmt|;
block|}
comment|//author (weiterer)
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"028B"
argument_list|)
condition|)
block|{
name|String
name|vorname
init|=
name|getSubfield
argument_list|(
literal|"d"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
name|String
name|nachname
init|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|author
operator|=
name|author
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|author
operator|=
literal|""
expr_stmt|;
block|}
name|author
operator|=
name|author
operator|.
name|concat
argument_list|(
name|vorname
operator|+
literal|" "
operator|+
name|nachname
argument_list|)
expr_stmt|;
block|}
comment|//editor
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"028C"
argument_list|)
condition|)
block|{
name|String
name|vorname
init|=
name|getSubfield
argument_list|(
literal|"d"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
name|String
name|nachname
init|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
block|{
name|editor
operator|=
name|editor
operator|.
name|concat
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editor
operator|=
literal|""
expr_stmt|;
block|}
name|editor
operator|=
name|editor
operator|.
name|concat
argument_list|(
name|vorname
operator|+
literal|" "
operator|+
name|nachname
argument_list|)
expr_stmt|;
block|}
comment|//title and subtitle
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"021A"
argument_list|)
condition|)
block|{
name|title
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|subtitle
operator|=
name|getSubfield
argument_list|(
literal|"d"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//publisher and address
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"033A"
argument_list|)
condition|)
block|{
name|publisher
operator|=
name|getSubfield
argument_list|(
literal|"n"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|address
operator|=
name|getSubfield
argument_list|(
literal|"p"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//year
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"011@"
argument_list|)
condition|)
block|{
name|year
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//year, volume, number, pages (year bei Zeitschriften (evtl. redundant mit 011@))
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"031A"
argument_list|)
condition|)
block|{
name|year
operator|=
name|getSubfield
argument_list|(
literal|"j"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|volume
operator|=
name|getSubfield
argument_list|(
literal|"e"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|number
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|pages
operator|=
name|getSubfield
argument_list|(
literal|"h"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|// 036D seems to contain more information than the other fields
comment|// overwrite information using that field
comment|// 036D also contains information normally found in 036E
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"036D"
argument_list|)
condition|)
block|{
comment|// 021 might have been present
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
comment|// convert old title (contained in "a" of 021A) to volume
if|if
condition|(
name|title
operator|.
name|startsWith
argument_list|(
literal|"@"
argument_list|)
condition|)
block|{
comment|// "@" indicates a number
name|title
operator|=
name|title
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// we nevertheless keep the old title data
block|}
name|number
operator|=
name|title
expr_stmt|;
block|}
comment|//title and subtitle
name|title
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|subtitle
operator|=
name|getSubfield
argument_list|(
literal|"d"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|volume
operator|=
name|getSubfield
argument_list|(
literal|"l"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//series and number
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"036E"
argument_list|)
condition|)
block|{
name|series
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|number
operator|=
name|getSubfield
argument_list|(
literal|"l"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|String
name|kor
init|=
name|getSubfield
argument_list|(
literal|"b"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|kor
operator|!=
literal|null
condition|)
block|{
name|series
operator|=
name|series
operator|+
literal|" / "
operator|+
name|kor
expr_stmt|;
block|}
block|}
comment|//note
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"037A"
argument_list|)
condition|)
block|{
name|note
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//edition
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"032@"
argument_list|)
condition|)
block|{
name|edition
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//isbn
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"004A"
argument_list|)
condition|)
block|{
name|String
name|isbn_10
init|=
name|getSubfield
argument_list|(
literal|"0"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
name|String
name|isbn_13
init|=
name|getSubfield
argument_list|(
literal|"A"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|isbn_10
operator|!=
literal|null
condition|)
block|{
name|isbn
operator|=
name|isbn_10
expr_stmt|;
block|}
if|if
condition|(
name|isbn_13
operator|!=
literal|null
condition|)
block|{
name|isbn
operator|=
name|isbn_13
expr_stmt|;
block|}
block|}
comment|// Hochschulschriftenvermerk
comment|// Bei einer Verlagsdissertation ist der Ort schon eingetragen
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"037C"
argument_list|)
condition|)
block|{
if|if
condition|(
name|address
operator|==
literal|null
condition|)
block|{
name|address
operator|=
name|getSubfield
argument_list|(
literal|"b"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|address
operator|=
name|removeSortCharacters
argument_list|(
name|address
argument_list|)
expr_stmt|;
block|}
name|String
name|st
init|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
if|if
condition|(
name|st
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|st
operator|.
name|contains
argument_list|(
literal|"Diss"
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"phdthesis"
expr_stmt|;
block|}
block|}
block|}
comment|//journal oder booktitle
comment|/* Problematiken hier: Sowohl fÃ¼r Artikel in              * Zeitschriften als fÃ¼r BeitrÃ¤ge in BÃ¼chern              * wird 027D verwendet. Der Titel muÃ je nach              * Fall booktitle oder journal zugeordnet              * werden. Auch bei Zeitschriften werden hier              * ggf. Verlag und Ort angegeben (sind dann              * eigentlich Ã¼berflÃ¼ssig), wÃ¤hrend bei              * BuchbeitrÃ¤gen Verlag und Ort wichtig sind              * (sonst in Kategorie 033A).              */
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"027D"
argument_list|)
condition|)
block|{
name|journal
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|booktitle
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|address
operator|=
name|getSubfield
argument_list|(
literal|"p"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
name|publisher
operator|=
name|getSubfield
argument_list|(
literal|"n"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
comment|//pagetotal
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"034D"
argument_list|)
condition|)
block|{
name|pagetotal
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
comment|// S, S. etc. entfernen
name|pagetotal
operator|=
name|pagetotal
operator|.
name|replaceAll
argument_list|(
literal|" S\\.?$"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
comment|// Behandlung von Konferenzen
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"030F"
argument_list|)
condition|)
block|{
name|address
operator|=
name|getSubfield
argument_list|(
literal|"k"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|entryType
operator|.
name|equals
argument_list|(
literal|"proceedings"
argument_list|)
condition|)
block|{
name|subtitle
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
name|entryType
operator|=
literal|"proceedings"
expr_stmt|;
block|}
comment|// Wenn eine Verlagsdiss vorliegt
if|if
condition|(
name|entryType
operator|.
name|equals
argument_list|(
literal|"phdthesis"
argument_list|)
condition|)
block|{
if|if
condition|(
name|isbn
operator|!=
literal|null
condition|)
block|{
name|entryType
operator|=
literal|"book"
expr_stmt|;
block|}
block|}
comment|//Hilfskategorien zur Entscheidung @article
comment|//oder @incollection; hier kÃ¶nnte man auch die
comment|//ISBN herausparsen als Erleichterung fÃ¼r das
comment|//Auffinden der Quelle, die Ã¼ber die
comment|//SRU-Schnittstelle gelieferten Daten zur
comment|//Quelle unvollstÃ¤ndig sind (z.B. nicht Serie
comment|//und Nummer angegeben werden)
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"039B"
argument_list|)
condition|)
block|{
name|quelle
operator|=
name|getSubfield
argument_list|(
literal|"8"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"046R"
argument_list|)
condition|)
block|{
if|if
condition|(
name|quelle
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|||
operator|(
name|quelle
operator|==
literal|null
operator|)
condition|)
block|{
name|quelle
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
block|}
comment|// URLs behandeln
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"tag"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"009P"
argument_list|)
condition|)
block|{
if|if
condition|(
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"occurrence"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"03"
argument_list|)
operator|||
name|datafield
operator|.
name|getAttribute
argument_list|(
literal|"occurrence"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"05"
argument_list|)
condition|)
block|{
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
name|url
operator|=
name|getSubfield
argument_list|(
literal|"a"
argument_list|,
name|datafield
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Abfangen von Nulleintraegen
if|if
condition|(
name|quelle
operator|==
literal|null
condition|)
block|{
name|quelle
operator|=
literal|""
expr_stmt|;
block|}
comment|// Nichtsortierzeichen entfernen
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|author
operator|=
name|removeSortCharacters
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
block|{
name|editor
operator|=
name|removeSortCharacters
argument_list|(
name|editor
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|title
operator|=
name|removeSortCharacters
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|subtitle
operator|!=
literal|null
condition|)
block|{
name|subtitle
operator|=
name|removeSortCharacters
argument_list|(
name|subtitle
argument_list|)
expr_stmt|;
block|}
comment|// Dokumenttyp bestimmen und Eintrag anlegen
if|if
condition|(
name|mak
operator|.
name|startsWith
argument_list|(
literal|"As"
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"misc"
expr_stmt|;
if|if
condition|(
name|quelle
operator|.
name|contains
argument_list|(
literal|"ISBN"
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"incollection"
expr_stmt|;
block|}
if|if
condition|(
name|quelle
operator|.
name|contains
argument_list|(
literal|"ZDB-ID"
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"article"
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|mak
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"misc"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mak
operator|.
name|startsWith
argument_list|(
literal|"O"
argument_list|)
condition|)
block|{
name|entryType
operator|=
literal|"online"
expr_stmt|;
block|}
comment|/*          * Wahrscheinlichkeit, dass ZDB-ID          * vorhanden ist, ist grÃ¶Ãer als ISBN bei          * BuchbeitrÃ¤gen. Daher bei As?-SÃ¤tzen am besten immer          * dann @incollection annehmen, wenn weder ISBN noch          * ZDB-ID vorhanden sind.          */
name|BibtexEntry
name|result
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entryType
argument_list|)
argument_list|)
decl_stmt|;
comment|// Zuordnung der Felder in AbhÃ¤ngigkeit vom Dokumenttyp
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|ImportFormatReader
operator|.
name|expandAuthorInitials
argument_list|(
name|author
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"editor"
argument_list|,
name|ImportFormatReader
operator|.
name|expandAuthorInitials
argument_list|(
name|editor
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|subtitle
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"subtitle"
argument_list|,
name|subtitle
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|publisher
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
name|publisher
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|year
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|address
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
name|address
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|series
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"series"
argument_list|,
name|series
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|edition
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"edition"
argument_list|,
name|edition
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isbn
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
name|isbn
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|issn
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
name|issn
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|number
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pagetotal
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"pagetotal"
argument_list|,
name|pagetotal
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pages
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|pages
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|volume
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|volume
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|journal
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ppn
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"ppn_GVK"
argument_list|,
name|ppn
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
name|url
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|note
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
name|note
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entryType
operator|.
name|equals
argument_list|(
literal|"article"
argument_list|)
condition|)
block|{
if|if
condition|(
name|journal
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|entryType
operator|.
name|equals
argument_list|(
literal|"incollection"
argument_list|)
condition|)
block|{
if|if
condition|(
name|booktitle
operator|!=
literal|null
condition|)
block|{
name|result
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
name|booktitle
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|getSubfield (String a, Element datafield)
specifier|private
name|String
name|getSubfield
parameter_list|(
name|String
name|a
parameter_list|,
name|Element
name|datafield
parameter_list|)
block|{
name|List
argument_list|<
name|Element
argument_list|>
name|liste
init|=
name|getChildren
argument_list|(
literal|"subfield"
argument_list|,
name|datafield
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Element
argument_list|>
name|iter
init|=
name|liste
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Element
name|subfield
init|=
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|subfield
operator|.
name|getAttribute
argument_list|(
literal|"code"
argument_list|)
operator|.
name|equals
argument_list|(
name|a
argument_list|)
condition|)
block|{
return|return
operator|(
name|subfield
operator|.
name|getTextContent
argument_list|()
operator|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
DECL|method|getChild (String name, Element e)
specifier|private
name|Element
name|getChild
parameter_list|(
name|String
name|name
parameter_list|,
name|Element
name|e
parameter_list|)
block|{
name|Element
name|result
init|=
literal|null
decl_stmt|;
name|NodeList
name|children
init|=
name|e
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|int
name|j
init|=
name|children
operator|.
name|getLength
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|j
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|test
init|=
name|children
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|test
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|Element
name|entry
init|=
operator|(
name|Element
operator|)
name|test
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|getTagName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
return|return
name|entry
return|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|getChildren (String name, Element e)
specifier|private
name|List
argument_list|<
name|Element
argument_list|>
name|getChildren
parameter_list|(
name|String
name|name
parameter_list|,
name|Element
name|e
parameter_list|)
block|{
name|List
argument_list|<
name|Element
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
name|NodeList
name|children
init|=
name|e
operator|.
name|getChildNodes
argument_list|()
decl_stmt|;
name|int
name|j
init|=
name|children
operator|.
name|getLength
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|j
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|test
init|=
name|children
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|test
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|Element
name|entry
init|=
operator|(
name|Element
operator|)
name|test
decl_stmt|;
if|if
condition|(
name|entry
operator|.
name|getTagName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
DECL|method|removeSortCharacters (String input)
specifier|private
name|String
name|removeSortCharacters
parameter_list|(
name|String
name|input
parameter_list|)
block|{
name|input
operator|=
name|input
operator|.
name|replaceAll
argument_list|(
literal|"\\@"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
return|return
name|input
return|;
block|}
block|}
end_class

end_unit

