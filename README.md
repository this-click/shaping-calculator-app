# Shaping Calculator App

An app to help shape my knitting projects.

Based on Roxanne Richardson's spreadsheet, this app supports my hobby and demonstrates data persistence using Room library.

https://docs.google.com/spreadsheets/d/1TTq3sI0qEq8_aaIz757MwZ3q_gAMhahi08CIr_TE39M

TODOs:
1. Display a toast after adding new items

Warning: The formula below might induce headache, read at your own risk.

Spreadsheet breakdown: 

c24 = math.floor(c20,1)
c20 = rounddown(shape_span/shape_rows,0)  = rounddown(c15/c14,0) = rounddown(overRows/shapingCalculated,0)

shape_span = C15
shape_rows = c14
c15 = tot_rows-1 = C7 -1
c7 = c6*c5 = shapingLength * rowGauge
tot_rows = c7 = C6 * c5 = shaping_len * row_gauge
c14 = totalIncs/incsPerRow - 1

So,

c14 = shapingCalculated = totalIncs/incsPerRow -1
c15 = overRows = shapingLength * rowGauge -1
basicShapingRateItr = C20 = rounddown(overRows/shapingCalculated,0)
shapingRateItr = c24 = math.floor(c20,1) = math.floor(basicShapingRateItr)

c21 = ROUND(MOD(shape_span,shape_rows),0) = ROUND(MOD(overRows ,shapingCalculated ),0)
c21 = remainder

rateFactorA = c14 - f25 = shapingCalculated - c21 = shapingCalculated - remainder
rateFactorB = F25 = c21 = remainder
