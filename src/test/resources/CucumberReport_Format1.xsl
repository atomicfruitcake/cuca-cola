<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Documentation</title>
                <style>
                    body {
                    background-color: #EEE;
                    color: #333;
                    font-family: Arial, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    }
                    h1, h2, h3, h4, h5, h6 {
                    border-bottom: 1px solid #DDD;
                    padding-bottom: 0.3em;
                    }
                    p {
                    line-height: 1.4em;
                    margin-bottom: 1.5em;
                    }
                    a { text-decoration: none; }
                    a:hover { text-decoration: underline; }
                    table {
                    border-collapse: collapse;
                    margin: 10px;
                    }
                    td {
                    border: 1px solid #DDD;
                    padding: 6px;
                    }
                    #container {
                    background-color: #FFF;
                    border-left: 1px solid #DDD;
                    border-right: 1px solid #DDD;
                    margin-left: 40px;
                    padding: 10px 20px;
                    width: 750px;
                    }
                    #container > ul {
                    margin-bottom: 40px;
                    }
                </style>
            </head>
            <body>
                <div id="container">
                    <h1>Features Documentation</h1>
                    <ul>
                        <xsl:for-each select="/objects/object">
                            <li>
                                <a href="#{translate(normalize-space(name),' ','-')}"><xsl:value-of select="name" /></a>
                                <ul>
                                    <xsl:for-each select="elements/element">
                                        <li>
                                            <a href="#{translate(normalize-space(name),' ','-')}"><xsl:value-of select="name" /></a>
                                        </li>
                                    </xsl:for-each>
                                </ul>
                            </li>
                        </xsl:for-each>
                    </ul>
                    <xsl:for-each select="/objects/object">
                        <h2><a name="{translate(normalize-space(name),' ','-')}"><xsl:value-of select="keyword" />: <xsl:value-of select="name" /></a></h2>
                        <xsl:apply-templates select="description"/>
                        <xsl:apply-templates select="elements/element"/>
                    </xsl:for-each>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="element">
        <h3><a name="{translate(normalize-space(name),' ','-')}"><xsl:value-of select="keyword" />: <xsl:value-of select="name" /></a></h3>
        <xsl:apply-templates select="description" />
        <p>
            <xsl:apply-templates select="steps" />
        </p>
        <xsl:if test="examples/example/rows/row/cells/cell != ''">
            <table>
                <xsl:apply-templates select="examples/example/rows/row" />
            </table>
        </xsl:if>
    </xsl:template>

    <xsl:template match="description">
        <xsl:if test=". != ''">
            <p>
                <xsl:value-of select="." />
            </p>
        </xsl:if>
    </xsl:template>

    <xsl:template match="step">
        <xsl:value-of select="keyword" /> <xsl:value-of select="name" />
        <xsl:if test="following-sibling::step">
            <br/>
        </xsl:if>
        <xsl:if test="rows/row/cells/cell != ''">
            <table>
                <xsl:apply-templates select="rows/row" />
            </table>
        </xsl:if>
    </xsl:template>

    <xsl:template match="row">
        <tr>
            <xsl:for-each select="cells/cell">
                <td>
                    <xsl:value-of select="." />
                </td>
            </xsl:for-each>
        </tr>
    </xsl:template>
</xsl:stylesheet>