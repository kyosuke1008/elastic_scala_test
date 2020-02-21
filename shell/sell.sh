#!/usr/bin/env bash
body=$(cat << EOF
{
  "order": {
    "units": "-100",
    "instrument": "USD_JPY",
    "timeInForce": "FOK",
    "type": "MARKET",
    "positionFill": "DEFAULT",
    "stopLossOnFill": {
      "price": "111.844"
    },
    "takeProfitOnFill": {
      "price": "111.256"
    }
  }
}
EOF
)

curl \
  -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer 182f823c2473178b15d7074e0d16ac00-3e71c772eea2fa45b4bd1f46cb4cdad2" \
  -d "$body" \
  "https://api-fxtrade.oanda.com/v3/accounts/001-009-2247015-001/orders"