<?php

namespace App\Imports;

use Illuminate\Support\Collection;
use Maatwebsite\Excel\Concerns\ToCollection;
use App\Models\Question;
 // Add this line to import the 'Question' class

class QuestionImport implements ToCollection
{
    /**
    * @param Collection $collection
    */
    public function collection(Collection $rows)
    {
        foreach ($rows as $row) 
        {
            $questionNo = (int) $row[0];
            $challengeNo = (int) $row[2];

            if ($questionNo == 0 && $challengeNo == 0) {
                continue; // Skip the header row
            }
            if (!is_int($questionNo) || !is_int($challengeNo)) {
                // Log an error or handle the case where values are not integers
                error_log("Invalid data type for QuestionNo or ChallengeNo");
                continue; 
            }// Skip this row
            Question::updateOrCreate(
                ['QuestionNo' => $questionNo],
                ['Question' => $row[1],
                'ChallengeNo' => $challengeNo,
                ]
            );
        }
    }
}
