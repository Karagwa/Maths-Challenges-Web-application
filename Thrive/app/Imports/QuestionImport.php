<?php

namespace App\Imports;

use Illuminate\Support\Collection;
use Maatwebsite\Excel\Concerns\ToCollection;
use App\Models\Question;
use Maatwebsite\Excel\Concerns\WithChunkReading;

class QuestionImport implements ToCollection, WithChunkReading
{
    public function collection(Collection $rows)
    {
        $data = [];

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
            }

            $data[] = [
                'QuestionNo' => $questionNo,
                'Question' => $row[1],
                'ChallengeNo' => $challengeNo,
            ];
        }

        Question::upsert($data, ['QuestionNo'], ['Question', 'ChallengeNo']);
    }

    public function chunkSize(): int
    {
        return 50; // Adjust chunk size as needed
    }
}
